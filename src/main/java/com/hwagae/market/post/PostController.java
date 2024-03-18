package com.hwagae.market.post;

import com.hwagae.market.category.CategoryEntity;
import com.hwagae.market.category.CategoryService;
import com.hwagae.market.event.EventDTO;
import com.hwagae.market.event.EventService;
import com.hwagae.market.file.FileEntity;
import com.hwagae.market.inquiry.InquiryDTO;
import com.hwagae.market.inquiry.InquiryService;
import com.hwagae.market.like.LikeDTO;
import com.hwagae.market.like.LikeService;
import com.hwagae.market.notice.NoticeDTO;
import com.hwagae.market.notice.NoticeService;
import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final LikeService likeService;
    private final EventService eventService;
    private final NoticeService noticeService;
    private final InquiryService inquiryService;

    @GetMapping("/post/save")
    public String saveForm(HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("userDTO = " + userDTO);

        if(userDTO != null){
            return "views/post/postSave";
        }else {
            return "views/user/login";
        }

    }

    @PostMapping("/post/save")
    public String save(@ModelAttribute PostDTO postDTO, HttpSession session) throws IOException {

        // 게시물 저장
        postService.save(postDTO);

        // 세이브 된 글 번호로 리다이렉트
        int firstPostNum = postService.findFirstPostNum(); // 첫 번째 게시물 번호 가져오기
        System.out.println("firstPostNum = " + firstPostNum);
        return "redirect:/post/" + firstPostNum;
    }

    @PostMapping("/post/edit")
    public String edit(@ModelAttribute PostDTO postDTO, HttpSession session) throws IOException {

        postService.save(postDTO);
        System.out.println("이거ㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓ = " + postDTO);
        //세이브 된 글 번호로 리다이렉트하기
        String post_num = String.valueOf(postService.edit(postDTO));
        System.out.println("savedPostNum = " + post_num);
        return "redirect:/post/"+post_num;
    }

/*

    @GetMapping("/post/saleList/{userNick}")
    public String Sale(@PathVariable("userNick") String userNick, Model model, HttpSession session){

        //get요청을 한 nick네임값
        System.out.println("클릭한 닉네임"+userNick);
        //세션값 불러오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        //일단 모든 게시물을 불러온다
        List<PostDTO> postDTOList = postService.findAll();


        //db에 저장된 닉네임 불러오기
        String user_nick = String.valueOf(userDTO.getUser_num());
        System.out.println("user_num으로 변환 = " + user_nick);

        List<PostDTO> userPostList = new ArrayList<>();
        for (PostDTO postDTO : postDTOList) {
            //db에 저장된 일련번호 불러오기
            String postUserNum = String.valueOf(postDTO.getUser_num());
            System.out.println("포스트넘?? = " + postUserNum);

            if (postUserNum.equals(user_nick)) {
                userPostList.add(postDTO);
                System.out.println("게시물 = " + postDTO);
            }
        }

        model.addAttribute("postList", userPostList);
        System.out.println("model = " + model);

        if(userNick.equals(userDTO.getUser_nick())){
        System.out.println("판매내역");
            return "views/myPage/saleList";
        }else {
            return "views/user/login";
        }
    }
*/

    // saleList 2트 및 페이징
    @GetMapping("/post/saleList")
    public String saleList(Model model, HttpSession session, @PageableDefault(page=1,sort="postNum",direction = Sort.Direction.DESC) Pageable pageable){

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Page<PostDTO> saleList = postService.findByUserNum(userDTO.getUser_num(), pageable);

        // 페이징
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/blockLimit)))-1)*blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<saleList.getTotalPages()) ? startPage + blockLimit - 1 : saleList.getTotalPages();

        model.addAttribute("saleList", saleList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        System.out.println("model = " + model);

        if(userDTO!=null){
            System.out.println("판매내역");
            return "views/myPage/saleList";
        }else {
            return "views/user/login";
        }
    }


    @GetMapping("/post/{postNum}")
    public String postDetail(@PathVariable("postNum") Integer postNum, Model model, HttpSession session) {
        //게시물목록 조회
        List<PostDTO> postDTOList = postService.findAll();

        //해당 postNum에 해당하는 게시물 가져오는 리스트
        List<PostDTO> userPostList = new ArrayList<>();

        UserDTO userDTO = (UserDTO) session.getAttribute("user");


        //각각의 게시물 확인
        for (PostDTO postDetail : postDTOList) {
            //게시물에 있는 post_num
            var postDetailNum = postDetail.getPost_num();

            // 현재 확인 중인 게시물이 postNum으로 요청된 게시물인 경우
            if (postDetailNum.equals(postNum)) {
                // 조회수 증가 메서드 호출
                postService.increasePostHits(postNum);
                // 해당 게시물의 카테고리 번호 가져오기
                Integer postCategoryNum = postDetail.getCategory_num();

                // 카테고리 번호를 사용하여 카테고리 정보 가져오기
                CategoryEntity categoryEntity = categoryService.getCategoryByCategoryNum(postCategoryNum);

                if (categoryEntity != null) {
                    postDetail.setCategoryName(categoryEntity.getCategoryName());
                }

////////////////////////////// post와 user 테이블 엮은 부분 ////////////////////////
                // 해당 게시물의 작성자 번호 가져오기
                Integer userNum = postDetail.getUser_num();

                // 해당 게시물의 작성자 번호를 사용하여 사용자 정보 가져오기
                UserEntity userEntity = userService.getUserByUserNum(userNum);

                if (userEntity != null) {
                    //postDetail에 user_nick, user_photo 세팅
                    postDetail.setUser_nick(userEntity.getUserNick());
                    postDetail.setUser_photo(userEntity.getUserPhoto());
                }
///////////////////////////////////////////////////////////////////////////////////
                userPostList.add(postDetail);
                System.out.println("게시물 = " + postDetail);
            }
        }
        if (userDTO != null){
            LikeDTO likeDTO = likeService.findByPostNum(postNum, userDTO);
            model.addAttribute("like",likeDTO);
            model.addAttribute("postDetail", userPostList);
        } else {
            model.addAttribute("postDetail", userPostList);
        }

        int countLike = likeService.countLikes(postNum);
        model.addAttribute("countLike",countLike);

        System.out.println("디테일");
        return "views/post/postDetail";
    }


    @GetMapping("/post/edit/{postNumArray}")
    public String postEdit(@PathVariable("postNumArray") Integer postNumArray, Model model) {
        System.out.println("넘어옵니까? = " + postNumArray);

        //게시물목록 조회
        List<PostDTO> postDTOList = postService.findAll();
        System.out.println("글 목록"+postDTOList);

        //해당 postNum에 해당하는 게시물 가져오는 리스트
        List<PostDTO> userPostList = new ArrayList<>();
        System.out.println("userPostList = " + userPostList);

        for (PostDTO postDetail : postDTOList) {
            var postDetailNum = postDetail.getPost_num();
            System.out.println("글에 있는 포스트 넘버 = " + postDetailNum);

            // 현재 확인 중인 게시물이 postNum으로 요청된 게시물인 경우
            if (postDetailNum.equals(postNumArray)) {
                // 해당 게시물의 카테고리 번호 가져오기
                Integer postCategoryNum = postDetail.getCategory_num();
                System.out.println("글에 있는 카테고리 넘버 = " + postCategoryNum);

                // 카테고리 번호를 사용하여 카테고리 정보 가져오기
                CategoryEntity categoryEntity = categoryService.getCategoryByCategoryNum(postCategoryNum);
                System.out.println("categoryEntity = " + categoryEntity);

                if (categoryEntity != null) {
                    postDetail.setCategoryName(categoryEntity.getCategoryName());
                    System.out.println("categoryEntity.getCategoryName() = " + categoryEntity.getCategoryName());
                }
                userPostList.add(postDetail);
                System.out.println("게시물 = " + postDetail);
            }
        }
        model.addAttribute("postEdit", userPostList);
        System.out.println("model = " + model);
        return "views/post/postEdit";
    }

/*

    @GetMapping("/post/category/{categoryNum}")
    public String Category(@PathVariable("categoryNum") Integer categoryNum, Model model, HttpSession session){

        //get요청을 한 category_num
        System.out.println("카테고리 번호 : "+categoryNum);

        //일단 모든 게시물을 불러온다
        List<PostDTO> postDTOList = postService.findAll();
        System.out.println("22222222222");

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        List<PostDTO> userPostList = new ArrayList<>();
        List<LikeDTO> likesList = new ArrayList<>();

        for (PostDTO postDTO : postDTOList) {
            // db에 저장된 category_num 불러오기
            Integer postCategoryNum = postDTO.getCategory_num();
            System.out.println("category_num?? = " + postCategoryNum);
            if (postCategoryNum.equals(categoryNum)) {
                userPostList.add(postDTO);
                System.out.println("게시물 = " + postDTO);
            }
        }

        model.addAttribute("category", userPostList);
        System.out.println("model = " + model);

        // 좋아요 정보를 담을 리스트
        if (userDTO != null) {
            for (PostDTO post : userPostList) {
                LikeDTO likeDTO = likeService.findByPostNum(post.getPost_num(), userDTO);
                likesList.add(likeDTO);
            }
            model.addAttribute("likesList", likesList);
            model.addAttribute("user", userDTO);
        }

        return "views/post/postCategory";
    }
*/

    //카테고리 넘기기 2트
    @GetMapping("/post/category/{categoryNum}")
    public String categoryList(@PathVariable("categoryNum") Integer categoryNum, Model model, HttpSession session, @PageableDefault(page=1) Pageable pageable){

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Page<PostDTO> categoryList = postService.findByCategoryNum(categoryNum,pageable);
        List<LikeDTO> likesList = new ArrayList<>();

        // 페이징
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/blockLimit)))-1)*blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<categoryList.getTotalPages()) ? startPage + blockLimit - 1 : categoryList.getTotalPages();

        model.addAttribute("category", categoryList);
        model.addAttribute("categoryNum", categoryNum);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        System.out.println("model = " + model);

        // 좋아요 정보를 담을 리스트
        if (userDTO != null) {
            for (PostDTO post : categoryList) {
                LikeDTO likeDTO = likeService.findByPostNum(post.getPost_num(), userDTO);
                likesList.add(likeDTO);
            }
            model.addAttribute("likesList", likesList);
            model.addAttribute("user", userDTO);
        }

        return "views/post/postCategory";
    }


    @GetMapping("/post/search")
    public String search(String keyword, Model model, HttpSession session, @PageableDefault(page=1,sort="postRegdate",direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostDTO> searchList = postService.search(keyword, pageable);

        // 페이징
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/blockLimit)))-1)*blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<searchList.getTotalPages()) ? startPage + blockLimit - 1 : searchList.getTotalPages();

        System.out.println("searchList = " + searchList);
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("유저정보! = " + userDTO);

        // 좋아요 정보를 담을 리스트
        List<LikeDTO> likesList = new ArrayList<>();

        // List에 있는 각 게시물에 대해 좋아요 정보를 가져옴
        if (userDTO != null){
            for (PostDTO post : searchList) {
                LikeDTO likeDTO = likeService.findByPostNum(post.getPost_num(), userDTO);
                likesList.add(likeDTO);
            }
            model.addAttribute("searchList",searchList);
            model.addAttribute("keyword",keyword);
            model.addAttribute("likesList",likesList);
            model.addAttribute("user",userDTO);
            //페이징 가져가기
            model.addAttribute("startPage",startPage);
            model.addAttribute("endPage",endPage);
        } else {
            model.addAttribute("searchList",searchList);
            model.addAttribute("keyword",keyword);
            // 페이징 가져가기
            model.addAttribute("startPage",startPage);
            model.addAttribute("endPage",endPage);
        }


        return "views/post/searchList";
    }

    @GetMapping("/post/delete/{postNum}")
    private String postDelete(@PathVariable String postNum, Model model){
        postService.deletePost(postNum);


        List<PostDTO> postDTOList = postService.findAll();
        model.addAttribute("postList", postDTOList);

        List<EventDTO> eventDTOList = eventService.findAll();
        model.addAttribute("eventList", eventDTOList);

        List<NoticeDTO> noticeDTOList = noticeService.findAll();
        model.addAttribute("noticeList", noticeDTOList);

        List<InquiryDTO> inquiryDTOList = inquiryService.findAll();
        model.addAttribute("inquiryList", inquiryDTOList);
        return "/views/user/index";
    }

}
