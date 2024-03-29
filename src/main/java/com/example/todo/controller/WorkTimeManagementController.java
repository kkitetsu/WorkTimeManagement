package com.example.todo.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.entity.LogsEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.form.WorkTimeRequest;
import com.example.todo.service.EmployeesInfoService;
import com.example.todo.utils.HashGenerator;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkTimeManagementController {

    /**
     * @author kk
     *
     * Employees information service.
     */
    @Autowired
    private EmployeesInfoService employeesInfoService;

    /**
     * @author kk
     *
     * Previous log to track if the user can clock in or not.
     */
    private LogsEntity previousLog;

    /**
     * @author kk
     *
     * Access to user my page.
     *
     * @return
     */
    @GetMapping(value="/userMyPage")
    public String UserMyPage(Model model, HttpSession session) {
        if (session.getAttribute("userFirstName") == null) {

            return "redirect:/home";
        }
        model.addAttribute("userName", session.getAttribute("userFirstName"));
        return "/userMyPage";
    }

    @GetMapping(value="/home")
    public String displayhome(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("logininfo", new LoginRequest() );
        return "home";
    }

    @GetMapping(value="/create")
    public String displaycreatepage(Model model, HttpSession session) {
        return "createAccount";
    }
    
    /**
     * Show the user's working time and working days.
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value="/detail")
    public String displaycreatepage1(Model model, HttpSession session) {
    	WorkTimeRequest workTimeRequest = new WorkTimeRequest();
    	workTimeRequest.setId((int)session.getAttribute("userId"));
    	model.addAttribute("workTimeRequest", workTimeRequest);
    	return "/detail";
    }

    /**
     * @author kk
     *
     * Create a new user.
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createUser(Model model, HttpSession session,
                                        @RequestParam("employee_id") String id,
                                        @RequestParam("loginPW") String pwd) {

    	// 新規ユーザIDとパスワードを記録
        EmployeesEntity employeeEntity = new EmployeesEntity();
        employeeEntity.setEmployee_id(Integer.parseInt(id));
        try {
			employeeEntity.setLoginPW(HashGenerator.generateHash(pwd));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

        // 上記のデータをsqlに保存
        try {
            employeesInfoService.createNewUser(employeeEntity);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "この会員はすでに登録されております");
            return "createAccount";
        }

        session.setAttribute("userFirstName", employeesInfoService.getAnEmployeeFirstName(Integer.parseInt(id)));
        session.setAttribute("userId", id);
        model.addAttribute("userName", employeesInfoService.getAnEmployeeFirstName(Integer.parseInt(id)));

        return "userMyPage";
    }

    /**
     * Logic for handling login process.
     * 
     * @param loginrequest
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String UserLogin( @ModelAttribute LoginRequest loginrequest,
                                            Model model, HttpSession session) {
    	
    	String hashedPassword = "";
    	String adminPassword  = "";
		try {
			hashedPassword = HashGenerator.generateHash(loginrequest.getLogin_pw());
			adminPassword  = HashGenerator.generateHash("4755");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	
    	loginrequest.setLogin_pw(hashedPassword);

        /** @author kk  If the login info is admin, jump to admin page. */
        if (loginrequest.getLogin_id() == 4755 && loginrequest.getLogin_pw().equals(adminPassword)) {
        	session.setAttribute("userId", "ADMIN");
            return "redirect:admin";
        }

        // ユーザのログイン情報でsqlに取得
        List<EmployeesEntity> user_info = employeesInfoService.login(loginrequest);
        if (user_info.isEmpty()) {
        	// もしログイン情報が間違いだったら、SQLは空
            model.addAttribute("errMsg", "社員番号もしくはパスワードが違います");
            model.addAttribute("logininfo", new LoginRequest() );
            return "/home";
        }

        // セッションにユーザの名前を追加。のちにHTMLアウトプットなどで使える
        session.setAttribute("userFirstName", user_info.get(0).getFirstname());
        session.setAttribute("userId", loginrequest.getLogin_id());
        model.addAttribute("userName", session.getAttribute("userFirstName"));
        return "userMyPage";
    }

    /**
     * @author kk
     *
     * Logic after button click in userMyPage.
     *
     * @param model
     * @param session
     * @return alertAndRedirect, clockinPage, or top page
     */
    @RequestMapping(value="/clockin", method=RequestMethod.POST)
    public String clockIn(Model model, HttpSession session, @RequestParam("action") String action,
                                                            @RequestParam("selectedOption") String selectedOption) {
        if (session.getAttribute("userFirstName") == null) {
            return "redirect:/home";
        }
        if (action.equals("clockin")) {
        	
        	// 前回の打刻記録を取得する
            previousLog = employeesInfoService.getLastLog(Integer.parseInt(session.getAttribute("userId").toString()));
            
            // もし新規ユーザだったら、前回の打刻記録は存在しない
            if (previousLog == null) {
            	// 打刻をし、データベースを更新し、アラート表示に行く
            	LogsEntity logsEntity = new LogsEntity();
                logsEntity.setApplicant("本人");
                logsEntity.setNote("XXXX");
                logsEntity.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                logsEntity.setStampTypeId(Integer.parseInt(selectedOption));
                employeesInfoService.insertLogs(logsEntity);
                model.addAttribute("isClockIn", true);
                return "/alertAndRedirect";
            }

            // 前回の打刻記録と、現在の打刻の時間を比較
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            long millisecondsDifference = currentTimestamp.getTime() - previousLog.getDatetime().getTime();
            long hoursDifference = TimeUnit.MILLISECONDS.toHours(millisecondsDifference);
            
            if (previousLog.getStampTypeId() == Integer.parseInt(selectedOption)
                    && hoursDifference < 15) {
            	// 前打刻と今回の打刻内容が一緒（出勤と出勤）
            	// 前打刻十五時間経過していなかったら再同打刻不能
                model.addAttribute("unableToClockIn", "前回の打刻から十五時間すぎていません");
                return "/alertAndRedirect";
            } else {
            	// 打刻をし、データベースを更新し、アラート表示にいく
            	LogsEntity logsEntity = new LogsEntity();
                logsEntity.setApplicant("本人");
                logsEntity.setNote("XXXX");
                logsEntity.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
                logsEntity.setStampTypeId(Integer.parseInt(selectedOption));
                employeesInfoService.insertLogs(logsEntity);
                
                if (previousLog.getStampTypeId() == Integer.parseInt(selectedOption)) {
                	// もし前回の打刻と一緒の内容だったら、前回で出勤か退勤を忘れている
                	model.addAttribute("enableForgotAlert", true);
                }
                model.addAttribute("isClockIn", true);
                return "/alertAndRedirect";
            }

        } else if (action.equals("checkHistory")) {
        	// 履歴確認クリック
            return "redirect:/userLogPage";
        } else {
        	// ログアウトボタンクリック
            session.invalidate();    // Logout and back to home
            return "redirect:/home";
        }
    }

    /**
     * @author kk
     * 
     * 打刻履歴画面。
     * 
     * @param currPage 現在のページ。デフォルト（最初）は 1
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/userLogPage")
    public String userLogPage(@RequestParam(defaultValue = "1") int currPage, Model model, HttpSession session) {
        if (session.getAttribute("userFirstName") == null) {
            return "redirect:/home";
        }
        
        // データベースから５件だけ取得する準備
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        int LogsSize = employeesInfoService.getLogsSize(userId);
        final int SUBLISTSIZE = 5;
        int startIndex = (currPage - 1) * SUBLISTSIZE;
        
        // データベースから指定の範囲のデータを取得
        List<LogsEntity> logs = employeesInfoService.getEmployeesLogs(userId, SUBLISTSIZE, startIndex);

        // HTMLに必要な変数を入力
        model.addAttribute("logs", logs);
        model.addAttribute("userName", session.getAttribute("userFirstName"));
        model.addAttribute("currentPage", currPage);
        model.addAttribute("maxPageNum", (int) (Math.ceil(LogsSize / SUBLISTSIZE)));

        // ローデータは 0, 1, 2, 3 なのでそれらを漢字に変換
        for (LogsEntity eachLog : logs) {
            int tmp = eachLog.getStampTypeId();
            switch (tmp) {
                case 0: eachLog.setStampTypeIdStr("出勤"); break;
                case 1: eachLog.setStampTypeIdStr("退勤"); break;
                case 2: eachLog.setStampTypeIdStr("外出"); break;
                case 3: eachLog.setStampTypeIdStr("復帰"); break;
                default: eachLog.setStampTypeIdStr(null); break;
            }
        }

        // 履歴画面表示
        return "userLogPage";
    }

}
