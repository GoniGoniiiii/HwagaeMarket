package com.hwagae.market.notice;

import com.hwagae.market.file.FileEntity;
import com.hwagae.market.file.FileRepository;
import lombok.RequiredArgsConstructor;
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
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final FileRepository fileRepository;

    public void save(NoticeDTO noticeDTO) throws IOException {
        if (noticeDTO.getNotice_upLoadFile().isEmpty()) {
            NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO);
            noticeRepository.save(noticeEntity);
        } else {
            NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO);
            Integer saveNum = noticeRepository.save(noticeEntity).getNoticeNum();
            NoticeEntity notice = noticeRepository.findById(saveNum).get();

            for (MultipartFile noticeFile : noticeDTO.getNotice_upLoadFile()) {
                String originalFilename = noticeFile.getOriginalFilename();

                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String file_url = System.currentTimeMillis() + "_" + originalFilename;
                    String savePath = "C:/image/" + file_url;
                    noticeFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(notice, file_url);
                    fileRepository.save(fileEntity);
                }else{
                    System.out.println("파일이 입력되지않았습니다~"+originalFilename);
                }
            }
        }//else문 끝임
    }

    public List<NoticeDTO> findAll() {
        //entity로 넘어온 객체를 다시 dto로 담아서 controller로 보내줘야함
        List<NoticeEntity> noticeEntityList = noticeRepository.findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (NoticeEntity noticeEntity : noticeEntityList) {
            noticeDTOList.add(NoticeDTO.toNoticeDTO(noticeEntity));
        }
        return noticeDTOList;
    }

    public NoticeDTO findByNum(Integer notice_num) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(notice_num);
        if (optionalNoticeEntity.isPresent()) {
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDTO noticeDTO = NoticeDTO.toNoticeDTO(noticeEntity);
            return noticeDTO;
        }
        return null;
    }

    //
//    public NoticeDTO update(NoticeDTO noticeDTO) throws IOException {
//        NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO);
//
//        if (!noticeDTO.getNotice_upLoadFile().isEmpty()) {
//            Integer saveNum = noticeRepository.save(noticeEntity).getNoticeNum();
//            NoticeEntity savedNotice = noticeRepository.findById(saveNum).get();
//
//            for (MultipartFile noticeFile : noticeDTO.getNotice_upLoadFile()) {
//                String originalFilename = noticeFile.getOriginalFilename();
//                String file_url = System.currentTimeMillis() + "_" + originalFilename;
//                String savePath = "C:/image/" + file_url;
//                noticeFile.transferTo(new File(savePath));
//
//                FileEntity fileEntity = FileEntity.toFileEntity(savedNotice, file_url);
//                fileEntity.updateFileUrl(file_url);
//                fileRepository.save(fileEntity);
//            }
//        } else {
//            noticeRepository.save(noticeEntity);
//
//        }
//        return findByNum(noticeDTO.getNotice_num());
//    }
    @Transactional
    public NoticeDTO update(NoticeDTO noticeDTO) throws Exception {
        if (noticeDTO.getNotice_num() != null) {
            // 기존 NoticeEntity 불러오기
            NoticeEntity existingNotice = noticeRepository.findById(noticeDTO.getNotice_num()).orElse(null);

            if (existingNotice != null) {
                deleteOldFiles(existingNotice);
                // Update existing notice
                existingNotice.setNoticeTitle(noticeDTO.getNotice_title());
                existingNotice.setNoticeContent(noticeDTO.getNotice_content());

                // Remove existing files
                existingNotice.getFileEntityList().clear();

                // Handle file uploads
                if (!noticeDTO.getNotice_upLoadFile().isEmpty()) {
                    for (MultipartFile noticeFile : noticeDTO.getNotice_upLoadFile()) {
                        String originalFilename = noticeFile.getOriginalFilename();

                        if (originalFilename != null && !originalFilename.isEmpty()) {
                            String file_url = System.currentTimeMillis() + "_" + originalFilename;
                            String savePath = "C:/image/" + file_url;
                            noticeFile.transferTo(new File(savePath));

                            // Create FileEntity and update fileUrl
                            FileEntity fileEntity = FileEntity.toFileEntity(existingNotice, file_url);
                            fileEntity.updateFileUrl(file_url);
                            fileRepository.save(fileEntity);
                        }else{
                            System.out.println("파일이 입력되지 않았어요!"+originalFilename);
                        }
                    }
                }

                // Save updated NoticeEntity
                noticeRepository.save(existingNotice);
                return findByNum(noticeDTO.getNotice_num());
            } else {
                throw new Exception("해당 번호로 된 공지사항이 존재하지 않습니다: " + noticeDTO.getNotice_num());
            }
        } else {
            throw new IllegalArgumentException("공지사항 번호가 필요합니다.");
        }
    }
    private void deleteOldFiles(NoticeEntity existingNotice) {
        for (FileEntity fileEntity : existingNotice.getFileEntityList()) {
            // Delete file from storage (you need to implement this method)
            deleteFileFromStorage(fileEntity.getFileUrl());

            // Delete file entity from database
            fileRepository.delete(fileEntity);
        }
    }
    private void deleteFileFromStorage(String fileUrl) {
        // 파일이 저장된 경로
        String storagePath = "C:/image/";

        // 파일 객체 생성
        File file = new File(storagePath + fileUrl);

        // 파일이 존재하면 삭제
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("파일 삭제 성공: " + file.getAbsolutePath());
            } else {
                System.err.println("파일 삭제 실패: " + file.getAbsolutePath());
            }
        } else {
            System.err.println("파일이 존재하지 않습니다: " + file.getAbsolutePath());
        }
    }

    public void delete(Integer notice_num) {
        noticeRepository.deleteById(notice_num);
    }
}