package com.hwagae.market.report;

import com.hwagae.market.file.FileEntity;
import com.hwagae.market.file.FileRepository;
import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    @Transactional
    public void save(ReportDTO reportDTO) throws IOException {
        if (reportDTO.getReport_upLoadFile().isEmpty()) {
            ReportEntity reportEntity = ReportEntity.toSaveEntity(reportDTO);
            reportRepository.save(reportEntity);
        } else {
            ReportEntity reportEntity = ReportEntity.toSaveEntity(reportDTO);
            Integer saveNum = reportRepository.save(reportEntity).getReportNum();
            ReportEntity report = reportRepository.findById(saveNum).get();

            for (MultipartFile reportFile : reportDTO.getReport_upLoadFile()) {
                String originalFileName = reportFile.getOriginalFilename();
                System.out.println("원래 파일명: " + originalFileName);

                if (originalFileName != null && !originalFileName.isEmpty()) {
                    String file_url = System.currentTimeMillis() + originalFileName;
                    String savePath = "C:/image/" + file_url;
                    reportFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(report, file_url);
                    fileRepository.save(fileEntity);
                } else {
                    System.out.println("파일이 입력되지 않았습니다!");
                }
            }
        }
    }
    @Transactional
    public List<ReportDTO> findAll() {
        List<ReportEntity> reportEntityList = reportRepository.findAll();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (ReportEntity reportEntity : reportEntityList) {
            reportDTOList.add(ReportDTO.toReportDTO(reportEntity));
        }
        return reportDTOList;
    }
    @Transactional
    public List<ReportDTO> findAllByUserNum(Integer user_num) {
        UserEntity userEntity = userRepository.findById(user_num).orElse(null);
        if (userEntity != null) {
            List<ReportEntity> reportEntityList = reportRepository.findAllByUserEntity(userEntity);
            List<ReportDTO> reportDTOList = new ArrayList<>();
            for (ReportEntity reportEntity : reportEntityList) {
                reportDTOList.add(ReportDTO.toReportDTO(reportEntity));
            }
            return reportDTOList;
        } else {
            return null;
        }
    }

    @Transactional
    public ReportDTO findByNum(Integer report_num) {
        Optional<ReportEntity> optionalReportEntity = reportRepository.findById(report_num);
        if (optionalReportEntity.isPresent()) {
            ReportEntity reportEntity = optionalReportEntity.get();
            ReportDTO reportDTO = ReportDTO.toReportDTO(reportEntity);
            return reportDTO;
        }
        return null;
    }
    @Transactional
    public Page<ReportDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; //한 페이지에 보여줄 글 갯수
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<ReportEntity> reportEntities =
                reportRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "reportNum")));

        System.out.println("reportEntities.getContent() = " + reportEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("reportEntities.getTotalElements() = " + reportEntities.getTotalElements()); // 전체 글갯수
        System.out.println("reportEntities.getNumber() = " + reportEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("reportEntities.getTotalPages() = " + reportEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("reportEntities.getSize() = " + reportEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("reportEntities.hasPrevious() = " + reportEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("reportEntities.isFirst() = " + reportEntities.isFirst()); // 첫 페이지 여부
        System.out.println("reportEntities.isLast() = " + reportEntities.isLast()); // 마지막 페이지 여부

        // 목록
        Page<ReportDTO> reportDTOS = reportEntities.map(report -> new ReportDTO(report.getReportNum(),
                report.getReportTitle(), report.getUserEntity().getUserName(),report.getReportDate(), report.getReportState()));
        return reportDTOS;
    }
    @Transactional
    public Page<ReportDTO> pagingByUserNum(Integer user_num, Pageable pageable) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(user_num);

        int page = pageable.getPageNumber()-1;
        int pageLimit = 10;

        Page<ReportEntity> reportEntities = reportRepository.findByUserEntity(
                userEntity,
                PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "reportNum"))
        );

        System.out.println("reportEntities.getContent() = " + reportEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("reportEntities.getTotalElements() = " + reportEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("reportEntities.getNumber() = " + reportEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("reportEntities.getTotalPages() = " + reportEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("reportEntities.getSize() = " + reportEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("reportEntities.hasPrevious() = " + reportEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("reportEntities.isFirst() = " + reportEntities.isFirst()); // 첫 페이지 여부
        System.out.println("reportEntities.isLast() = " + reportEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<ReportDTO> reportDTOS = reportEntities.map(report -> new ReportDTO(
                report.getReportNum(),
                report.getReportTitle(),
                report.getReportState(),
                report.getReportDate()
        ));
        return reportDTOS;
    }
    @Transactional
    public void delete(Integer report_num){reportRepository.deleteById(report_num);}
    @Transactional
    // 전체 신고 수를 반환하는 메서드
    public int getTotalReports() {
        return reportRepository.getTotalReportsCount();
    }

    @Transactional    // 처리 완료된 신고 수를 반환하는 메서드
    public int getCompletedReports() {
        return reportRepository.getCompletedReportsCount();
    }
    @Transactional
    public List<ReportDTO> findReportsByConditions(String reportSphone, String reportSaccount, String reportSnick) {
        List<ReportEntity> reportEntities = reportRepository.findReportsByConditions(reportSphone, reportSaccount, reportSnick);
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (ReportEntity reportEntity : reportEntities) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setReport_Snick(reportEntity.getReportSnick());
            reportDTO.setReport_Saccount(reportEntity.getReportSaccount());
            reportDTO.setReport_Sphone(reportEntity.getReportSphone());
            reportDTO.setReport_date(reportEntity.getReportDate());
            reportDTO.setUser_num(reportEntity.getUserEntity().getUserNum());
            reportDTOList.add(reportDTO);
        }
        return reportDTOList;
    }
}