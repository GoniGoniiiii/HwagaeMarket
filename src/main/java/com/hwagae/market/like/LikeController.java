package com.hwagae.market.like;

import com.hwagae.market.post.PostDTO;
import com.hwagae.market.post.PostService;
import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity likeProc(@ModelAttribute LikeDTO likeDTO, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        likeDTO.setUserNum(userDTO.getUser_num());
        System.out.println("LikeDTO = "+likeDTO);
        Boolean likeResult = likeService.like(likeDTO);
        if(likeResult){
            LikeDTO like = likeService.findByPostNum(likeDTO.getPostNum(),userDTO);

            return new ResponseEntity<>(like, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("게시글 존재안함",HttpStatus.NOT_FOUND);
        }
    }

    // 좋아요 List
    @GetMapping("/myPage/like")
    public String likedPostList(HttpSession session, Model model){

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        List<PostDTO> likedPosts = likeService.findLikedPostsByUserNum(userDTO.getUser_num());

        // 좋아요 정보를 담을 리스트
        List<LikeDTO> likesList = new ArrayList<>();

        // List에 있는 각 게시물에 대해 좋아요 정보를 가져옴
        for (PostDTO post : likedPosts) {
            LikeDTO likeDTO = likeService.findByPostNum(post.getPost_num(), userDTO);
            likesList.add(likeDTO);
        }
        Collections.reverse(likedPosts);

        model.addAttribute("likedPosts",likedPosts);
        model.addAttribute("likesList",likesList);

        return "views/myPage/like";
    }

}