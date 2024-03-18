package com.hwagae.market.user;

import com.hwagae.market.email.EmailController;
import com.hwagae.market.like.LikeService;
import com.hwagae.market.post.PostDTO;
import com.hwagae.market.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailController emailController;
    private final PostService postService;
    private final LikeService likeService;

    @GetMapping("/user/join")
    public String joinForm(){
        System.out.println("회원가입 페이지");
        return "views/user/join";
    }

    @PostMapping("/user/join")
    public String join(@ModelAttribute UserDTO userDTO){
        if ("ok".equals(emailController.getEmailAuthResult())) {
            System.out.println("후에엥"+emailController.getEmailAuthResult());
            System.out.println("UserController.save");
            System.out.println("userDTO = " + userDTO);
            userService.save(userDTO);
            System.out.println("회원가입 완료");
            return "redirect:/user/login?success=true"; // 회원가입 성공 시 login 페이지로 리다이렉트하고 success=true 파라미터를 전달
        }else {
            return "redirect:/user/join?success=false";
        }
    }

    @PostMapping("/user/id-check")
    public @ResponseBody String idCheck(@RequestParam("user_id") String user_id) {
        System.out.println("userId = " + user_id);
        String checkResult = userService.idCheck(user_id);
        return checkResult;
    }

    @PostMapping("/user/nick-check")
    public @ResponseBody String nickCheck(@RequestParam("user_nick") String user_nick) {
        System.out.println("userNick = " + user_nick);
        String checkResult = userService.nickCheck(user_nick);
        return checkResult;
    }


    @GetMapping("/user/login")
    public String LoginForm() {
        System.out.println("로그인 페이지");
        return "views/user/login";
    }

    @PostMapping("user/login")
    public String Login(@ModelAttribute UserDTO userDTO, HttpSession session, Model model){
        //글 목록 가져오기
        List<PostDTO> postDTOList = postService.findAll();
        model.addAttribute("postList", postDTOList);

        UserDTO result = userService.login(userDTO);
        if(result != null){
            session.setAttribute("user", result);
            if(result.getUser_id().equals("admin")){
                return  "redirect:/admin/adminMenu";
            }
            System.out.println("로그인 성공");
            return "/views/user/index";
        }else{
            return "redirect:/user/login?loginFailed=true";
        }
    }


    @GetMapping("/user/logout")
    public String Logout(HttpSession session) {
        session.invalidate();
        System.out.println("로그아웃");
        return "views/user/login";
    }


    @GetMapping("/myPage")
    public String MyPage(Model model, HttpSession session, @PageableDefault(page=1,sort="postNum",direction = Sort.Direction.DESC) Pageable pageable) {
        //세션정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        //세션의 user_num을 문자열 user_num으로 설정
        if (userDTO == null) {
            // 세션 값이 없을 경우 로그인 페이지로 리다이렉트
            return "views/user/login";
        }
        String user_num = String.valueOf(userDTO.getUser_num());
        System.out.println("세션 = " + user_num);
        //게시물목록 조회
        List<PostDTO> postDTOList = postService.findAll();

        //해당하는 게시물 가져오는 리스트
        List<PostDTO> userPostList = new ArrayList<>();
        for (PostDTO postDTO : postDTOList) {
            String postUser_num = String.valueOf(postDTO.getUser_num());
            // userNum 값을 사용하여 원하는 작업 수행
            if (postUser_num.equals(user_num)) {
                userPostList.add(postDTO);
                System.out.println("게시물 = " + postDTO);
            }
        }
        Page<PostDTO> saleList = postService.findByUserNum(userDTO.getUser_num(), pageable);

        // 페이징
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/blockLimit)))-1)*blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<saleList.getTotalPages()) ? startPage + blockLimit - 1 : saleList.getTotalPages();

        List<PostDTO> likedPosts = likeService.findLikedPostsByUserNum(userDTO.getUser_num());
        int likedPostsCount = likedPosts.size();

        model.addAttribute("likeCount", likedPostsCount);
        model.addAttribute("postList", userPostList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("saleList", saleList);

        System.out.println("model = " + model);

        System.out.println("마이페이지");
        return "views/myPage/myPage";
    }

    @GetMapping("/userPage/{postNick}")
    public String userPage(@PathVariable("postNick") String postNick, Model model){
        //선택 된 유저 정보 가져오기
        UserDTO userDTO = userService.getUserByUserNick(postNick);
        //선택 된 유저 정보에서 문자열 user_num 세팅
        String user_num = String.valueOf(userDTO.getUser_num());
        System.out.println("user_num = " + user_num);



        //게시물목록 조회
        List<PostDTO> postDTOList = postService.findAll();

        //해당하는 게시물 가져오는 리스트
        List<PostDTO> userPostList = new ArrayList<>();
        //각각의 게시물 확인
        for (PostDTO postDTO : postDTOList) {
            //post리스트에서 문자열 user_num 세팅
            String postUser_num = String.valueOf(postDTO.getUser_num());
            System.out.println("postUserNum = " + postUser_num);

            // user_num 값을 사용하여 원하는 작업 수행
            if (postUser_num.equals(user_num)) {
                userPostList.add(postDTO);
                System.out.println("게시물 = " + postDTO);
            }
        }

        model.addAttribute("postList", userPostList);
        model.addAttribute("userInfo", userDTO);

        System.out.println("model = " + model);

        System.out.println("유저페이지");
        if (userDTO != null){
            return "views/myPage/userPage";
        }else {
            return "views/user/login";
        }
    }



    @GetMapping("/user/find")
    public String Find(){
        System.out.println("아이디 비밀번호 찾기");
        return "views/user/find";
    }

    @PostMapping("/user/findID")
    public String findID(@ModelAttribute UserDTO userDTO, Model model) {
        String result = userService.findID(userDTO); // 반환 타입을 String으로 변경
        System.out.println("ID 찾기 = " + result);
        System.out.println("ID 정보 = " + userDTO);
        model.addAttribute("findID", result); // Thymeleaf에서 사용할 수 있도록 모델에 추가
        return "views/user/result";
    }

    @PostMapping("/user/findPW")
    public String findPW(@ModelAttribute UserDTO userDTO, Model model) {
        UserDTO result = userService.findPW(userDTO); // findPW 메서드 호출로 변경
        System.out.println("PW 찾기 = " + result);
        System.out.println("PW 정보 = " + userDTO);
        model.addAttribute("findPW", result.getUser_pw()); // Thymeleaf에서 사용할 수 있도록 모델에 추가
        return "views/user/result";
    }


    @GetMapping("/myPage/userUpdate")
    public String Update(HttpSession session, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserDTO updateInfo = userService.updateForm(userDTO.getUser_id());
        model.addAttribute("updateInfo", updateInfo);
        System.out.println("updateInfo = " + updateInfo);
        System.out.println("session = " + session + ", model = " + model);
        System.out.println("회원정보 수정");
        if(userDTO != null){
            return "views/myPage/userUpdate";
        }else {
            return "views/user/login";
        }
    }

    @PostMapping("/user/pwUpdate")
    public String UpdatePW(@ModelAttribute UserDTO userDTO, HttpSession session) throws IOException {
        // 세션에 저장된 사용자 정보 가져오기
        UserDTO sessionUser = (UserDTO) session.getAttribute("user");
        // 세션의 user_num과 폼에서 전송된 user_num이 일치하는 경우에만 수행

        if (sessionUser.getUser_num().equals(userDTO.getUser_num())) {
            // 비밀번호 업데이트 수행
            userService.updatePw(userDTO);
            // 세션에서 기존 사용자 정보 제거
            session.removeAttribute("user");
            // 업데이트된 사용자 정보를 세션에 설정
            session.setAttribute("user", sessionUser);
        }

        return "redirect:/myPage/userUpdate";
    }


    @PostMapping("/user/nickUpdate")
    public String UpdateNick(@ModelAttribute UserDTO userDTO, HttpSession session) throws IOException {
        userService.updateNick(userDTO);
        UserDTO updatedUser = userService.login(userDTO);

        UserDTO sessionUser = (UserDTO) session.getAttribute("user");
        // 사용자 정보 업데이트
        sessionUser.setUser_nick(userDTO.getUser_nick()); // 닉네임으로 변경 예시
        // 세션에서 기존 사용자 정보 제거
        session.removeAttribute("user");
        // 업데이트된 사용자 정보를 세션에 설정
        session.setAttribute("user", sessionUser);

        return "redirect:/myPage/userUpdate";
    }


    @PostMapping("/user/locationUpdate")
    public String UpdateLocation(@ModelAttribute UserDTO userDTO, HttpSession session) throws IOException {
        userService.updateLocation(userDTO);
        UserDTO updatedUser = userService.login(userDTO);

        UserDTO sessionUser = (UserDTO) session.getAttribute("user");
        // 사용자 정보 업데이트
        sessionUser.setUser_location2(userDTO.getUser_location2()); // 닉네임으로 변경 예시
        // 세션에서 기존 사용자 정보 제거
        session.removeAttribute("user");
        // 업데이트된 사용자 정보를 세션에 설정
        session.setAttribute("user", sessionUser);

        return "redirect:/myPage/userUpdate";
    }

    @PostMapping("/user/photoUpdate")
    public String UpdatePhoto(@ModelAttribute UserDTO userDTO, HttpSession session) throws IOException {
        MultipartFile upLoadFile = userDTO.getUpLoadFile();
        if (upLoadFile != null && !upLoadFile.isEmpty()) {
            String fileName = System.currentTimeMillis()+ "_" + upLoadFile.getOriginalFilename();
            System.out.println("Uploaded file name: " + fileName);

            // 파일을 서버에 저장
            upLoadFile.transferTo(new File("C:/image/" + fileName));

            // 사용자 정보 업데이트
            userDTO.setUser_photo(fileName);
            userService.updatePhoto(userDTO);

            UserDTO sessionUser = (UserDTO) session.getAttribute("user");
            // 사용자 정보 업데이트
            sessionUser.setUser_photo(fileName);
            // 세션에서 기존 사용자 정보 제거
            session.removeAttribute("user");
            // 업데이트된 사용자 정보를 세션에 설정
            session.setAttribute("user", sessionUser);

        }
        return "redirect:/myPage/userUpdate";
    }


    @GetMapping("/myPage/purchaseList/{userNick}")
    public String Purchase(@PathVariable("userNick") String userNick){
        System.out.println("구매내역");
        return "views/myPage/purchaseList";
    }


    @GetMapping("/myPage/like/{userNick}")
    public String Like(@PathVariable("userNick") String userNick){
        System.out.println("찜한 상품");
        return "views/myPage/like";
    }

    @GetMapping("/myPage/unRegister/{userNick}")
    public String UnRegister(@PathVariable("userNick") String userNick){
        System.out.println("회원탈퇴");
        return "views/myPage/unRegister";
    }

    @PostMapping("/myPage/unRegister")
    public String UnRegister(@RequestParam("user_id") String userId,
                             @RequestParam("user_pw") String userPw,
                             HttpSession session) {
        // 입력한 ID와 비밀번호로 로그인 시도
        UserDTO loginAttempt = new UserDTO();
        loginAttempt.setUser_id(userId);
        loginAttempt.setUser_pw(userPw);
        UserDTO user = userService.login(loginAttempt);

        // 사용자가 존재하고, 입력한 비밀번호가 일치하는 경우에만 삭제 진행
        if (user != null && user.getUser_pw().equals(userPw)) {
            userService.deleteUser(userId);
            session.invalidate(); // 세션 무효화
            return "views/user/login"; // 회원탈퇴 후 로그인 페이지로 리다이렉트
        } else {
            // ID나 비밀번호가 일치하지 않는 경우
            return "views/myPage/unRegister"; // 회원탈퇴 페이지로 다시 이동
        }
    }



}
