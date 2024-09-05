package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MenuServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 處理 GET 請求，可選擇轉發或重定向到首頁
        response.sendRedirect("index.jsp"); // 如果有首頁，可以設定為首頁
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String jspLocation = "";

        // 根據不同的 action 決定轉發到哪個 JSP
        if ("menu".equals(action)) {
            jspLocation = "/WEB-INF/groupbuy/frontend/main.jsp";
        } else if ("cart".equals(action)) {
            jspLocation = "/WEB-INF/groupbuy/frontend/cart.jsp";
        } else if ("login".equals(action)) {
        	HttpSession session = request.getSession(false);
        	if(session != null) {
        		session.invalidate();
        	}
        	
            jspLocation = "/login.jsp";
        } else {
            // 預設處理，若 action 不符合預期，可設定跳轉至錯誤頁面或首頁
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
            return;
        }

        // 轉發到相應的 JSP
        RequestDispatcher rd = request.getRequestDispatcher(jspLocation);
        rd.forward(request, response);
    }
}
