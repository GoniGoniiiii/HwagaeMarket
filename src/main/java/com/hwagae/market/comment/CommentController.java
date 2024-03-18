package com.hwagae.market.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.ManyToOne;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/myPage/inquiry")
    public ResponseEntity qna_save(@ModelAttribute CommentDTO commentDTO){
        System.out.println("commentDTO = " + commentDTO);
        Integer result=commentService.qna_save(commentDTO);
        if(result!=null){
            List<CommentDTO> commentDTOList=commentService.qna_findAll(commentDTO.getQna_num());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 문의글이 존재하지 않습니다!",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/myPage/report")
    public ResponseEntity report_save(@ModelAttribute CommentDTO commentDTO){
        System.out.println("commentDTO = " + commentDTO);
        Integer result=commentService.report_save(commentDTO);
        if(result!=null){
            List<CommentDTO> commentDTOList=commentService.report_findAll(commentDTO.getReport_num());
            return new ResponseEntity<>(commentDTOList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 신고가 존재하지않습니다!",HttpStatus.NOT_FOUND);
        }
    }

}