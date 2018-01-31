package com.iflytek.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.iflytek.domain.People;
import com.iflytek.utils.BeanFactory;
import com.iflytek.utils.LoggerUtils;
import com.iflytek.web.service.PmanagerService;

@WebServlet("/addMember")
public class AddMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddMemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		People people = new People();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> parseRequest = upload.parseRequest(request);
			for(FileItem item : parseRequest){
				boolean formField = item.isFormField();
				if(formField){
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					map.put(fieldName, fieldValue);
				}else{
					if(item.getName().isEmpty()) {
						map.put("photo", null);
					}else {
						String fileName = item.getName();
						String path = this.getServletContext().getRealPath("upload");
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(path+"/"+fileName);
						IOUtils.copy(in, out);
						in.close();
						out.close();
						item.delete();
						map.put("photo", "upload/"+fileName);
					}
				}
			}
			BeanUtils.populate(people, map);
			PmanagerService ps = (PmanagerService) BeanFactory.getBean("PmanagerService");
			
			ps.addMember(people);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		People pmPeople=(People) session.getAttribute("people");
		
		String logInfo=pmPeople.getPeopleName()+"添加了"+people.getPeopleName();
		LoggerUtils.getInstance().writeLog(logInfo);
		
		session.removeAttribute("peopleList");
		request.getRequestDispatcher("/pmanager").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
