package com.hwagae.market.comment;

import com.hwagae.market.inquiry.InquiryEntity;
import com.hwagae.market.inquiry.InquiryRepository;
import com.hwagae.market.report.ReportEntity;
import com.hwagae.market.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final InquiryRepository inquiryRepository;
    private final ReportRepository reportRepository;

    public Integer qna_save(CommentDTO commentDTO){
        System.out.println("qna_num값= " + commentDTO.getQna_num());
        Optional<InquiryEntity> optionalInquiryEntity=inquiryRepository.findById(commentDTO.getQna_num());
        if(optionalInquiryEntity.isPresent()){
            InquiryEntity inquiryEntity=optionalInquiryEntity.get();
            CommentEntity commentEntity=CommentEntity.toSaveEntity(commentDTO,inquiryEntity);

            Integer result= commentRepository.save(commentEntity).getCommentNum();
            if(result!=null){
                if("admin".equals(commentDTO.getComment_writer())){
                    inquiryEntity.setQnaStatus(1);
                    inquiryRepository.save(inquiryEntity);
                }
            }
            return result;
        }else{
            return null;
        }
    }

    public List<CommentDTO> qna_findAll(Integer qna_num){
        InquiryEntity inquiryEntity=inquiryRepository.findById(qna_num).get();

        List<CommentEntity> commentEntityList=commentRepository.findAllByInquiryEntityOrderByCommentNumDesc(inquiryEntity);

        List<CommentDTO> commentDTOList=new ArrayList<>();
        for(CommentEntity commentEntity: commentEntityList){
            CommentDTO commentDTO=CommentDTO.toCommentDTO_Qna(commentEntity,qna_num);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public List<CommentDTO> report_findAll(Integer report_num){
        ReportEntity reportEntity=reportRepository.findById(report_num).get();

        List<CommentEntity> commentEntityList=commentRepository.findAllByReportEntityOrderByCommentNumDesc(reportEntity);

        List<CommentDTO> commentDTOList=new ArrayList<>();
        for(CommentEntity commentEntity:commentEntityList){
            CommentDTO commentDTO=CommentDTO.toCommentDTO_Report(commentEntity,report_num);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }


    public Integer report_save(CommentDTO commentDTO){
        System.out.println("report_num값= " + commentDTO.getReport_num());
        Optional<ReportEntity> optionalReportEntity=reportRepository.findById(commentDTO.getReport_num());
        if(optionalReportEntity.isPresent()){
            ReportEntity reportEntity=optionalReportEntity.get();
            CommentEntity commentEntity=CommentEntity.toSaveEntity(commentDTO,reportEntity);

            Integer result= commentRepository.save(commentEntity).getCommentNum();
            if(result!=null){
                if("관리자".equals(commentDTO.getComment_writer())){
                    reportEntity.setReportState(1);
                    reportRepository.save(reportEntity);
                }
            }
            return result;
        }else{
            return null;
        }
    }

}