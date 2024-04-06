package com.hwagae.market.post;

import com.hwagae.market.file.FileEntity;
import com.hwagae.market.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.util.*;
//ip로 조회수 증가
import javax.servlet.http.HttpServletRequest;
//session으로 조회수 증가
import javax.servlet.http.HttpSession;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    //ip로 조회수 증가
    private final HttpServletRequest request;
    //session으로 조회수 증가
    private final HttpSession httpSession;


    public void save(PostDTO postDTO) throws IOException {
        if (postDTO.getPost_upLoadFile().isEmpty()) {
            PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
            postRepository.save(postEntity);
        } else {
            PostEntity postEntity = PostEntity.toSaveFileEntity(postDTO);
            Integer postNum = postRepository.save(postEntity).getPostNum();
            PostEntity post = postRepository.findById(postNum).get();

            for (MultipartFile postFile : postDTO.getPost_upLoadFile()) {
                String originalFilename = postFile.getOriginalFilename();

                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String file_url = System.currentTimeMillis() + "_" + originalFilename;
                    String savePath = "Y:/HDD1/image/" + file_url;
                    postFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(post, file_url);
                    fileRepository.save(fileEntity);
                } else {
                    System.out.println("파일이 입력되지않았습니다~" + originalFilename);
                }
            }
        }
    }


    public PostDTO update(PostDTO postDTO) throws Exception {
        if (postDTO.getPost_num() != null) {
            PostEntity postEntity = postRepository.findById(postDTO.getPost_num()).orElse(null);

            if(postEntity != null){
                deleteOldFiles(postEntity);

                postEntity.setPostTitle(postDTO.getPost_title());
                postEntity.setPostContent(postDTO.getPost_content());
                postEntity.setPostPrice(postDTO.getPost_price());
                postEntity.setPostRegdate(postDTO.getPost_regdate());
                postEntity.setPostUpdate(postDTO.getPost_update());
                postEntity.setPostLocation(postDTO.getPost_location());
                postEntity.setPostLocation2(postDTO.getPost_location2());
                postEntity.setPostHits(postDTO.getPost_hits());
                postEntity.setPostLike(postDTO.getPost_like());
                postEntity.setPostSaleState(postDTO.getPost_saleState());
                postEntity.setPostProductState(postDTO.getPost_productState());

                postEntity.getFileEntityList().clear();

                if (!postDTO.getPost_upLoadFile().isEmpty()){
                    for (MultipartFile postFile : postDTO.getPost_upLoadFile()){
                        String originalFilename = postFile.getOriginalFilename();

                        if (originalFilename != null && !originalFilename.isEmpty()){
                            String file_url = System.currentTimeMillis() + "_" + originalFilename;
                            String savePath = "Y:/HDD1/image/" + file_url;
                            postFile.transferTo(new File(savePath));

                            FileEntity fileEntity = FileEntity.toFileEntity(postEntity, file_url);
                            fileEntity.updateFileUrl(file_url);
                            fileRepository.save(fileEntity);
                        }else{
                            System.out.println("파일이 입력되지 않았어요!"+originalFilename);
                        }
                    }
                }
                // Save updated NoticeEntity
                postRepository.save(postEntity);
                return findByNum(postDTO.getPost_num());
            } else {
                throw new Exception("해당 번호로 된 공지사항이 존재하지 않습니다: " + postDTO.getPost_num());
            }
        } else {
            throw new IllegalArgumentException("공지사항 번호가 필요합니다.");
        }
    }

    public PostDTO findByNum(Integer post_num) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(post_num);
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);
            return postDTO;
        }
        return null;
    }
    private void deleteOldFiles(PostEntity postEntity) {
        for (FileEntity fileEntity : postEntity.getFileEntityList()) {
            // Delete file from storage (you need to implement this method)
            deleteFileFromStorage(fileEntity.getFileUrl());

            // Delete file entity from database
            fileRepository.delete(fileEntity);
        }
    }

    private void deleteFileFromStorage(String fileUrl) {
        // 파일이 저장된 경로
        String storagePath = "Y:/HDD1/image/";

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


    public void updatee(PostDTO postDTO) throws IOException {
        if (postDTO.getPost_upLoadFile().isEmpty()) {
            PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
            postRepository.save(postEntity);


        } else {
            PostEntity postEntity = PostEntity.toSaveFileEntity(postDTO);
            Integer postNum = postRepository.save(postEntity).getPostNum();
            PostEntity post = postRepository.findById(postNum).get();

            for (MultipartFile postFile : postDTO.getPost_upLoadFile()) {
                String originalFilename = postFile.getOriginalFilename();

                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String file_url = System.currentTimeMillis() + "_" + originalFilename;
                    String savePath = "Y:/HDD1/image/" + file_url;
                    postFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(post, file_url);
                    fileRepository.save(fileEntity);
                } else {
                    System.out.println("파일이 입력되지않았습니다~" + originalFilename);
                }
            }
        }
    }

    
    //상태만 변경
    public void stateUpdate(PostDTO postDTO) throws IOException {
        if (postDTO.getPost_upLoadFile().isEmpty()) {
            PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
            postRepository.save(postEntity);


        } else {
            PostEntity postEntity = PostEntity.toSaveFileEntity(postDTO);
            Integer postNum = postRepository.save(postEntity).getPostNum();
            PostEntity post = postRepository.findById(postNum).get();
            postEntity.getFileEntityList().clear();

            for (MultipartFile postFile : postDTO.getPost_upLoadFile()) {
                String originalFilename = postFile.getOriginalFilename();

                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String file_url = System.currentTimeMillis() + "_" + originalFilename;
                    String savePath = "Y:/HDD1/image/" + file_url;
                    postFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(post, file_url);
                    fileRepository.save(fileEntity);
                } else {
                    System.out.println("파일이 입력되지않았습니다~" + originalFilename);
                }
            }
        }
    }

/*
    //상태만 변경
    public long stateEdit(PostDTO postDTO) throws IOException {
        if (postDTO.getPost_upLoadFile().isEmpty()) {
            PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
            postRepository.save(postEntity);
        } else {
            PostEntity postEntity = PostEntity.toSaveFileEntity(postDTO);
            Integer postNum = postRepository.save(postEntity).getPostNum();
            PostEntity post = postRepository.findById(postNum).get();

            for (MultipartFile postFile : postDTO.getPost_upLoadFile()) {
                String originalFilename = postFile.getOriginalFilename();

                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String file_url = System.currentTimeMillis() + "_" + originalFilename;
                    String savePath = "Y:/HDD1/image/" + file_url;
                    postFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(post, file_url);
                    fileRepository.save(fileEntity);
                } else {
                    System.out.println("파일이 입력되지않았습니다~" + originalFilename);
                }
            }
        }
        return postDTO.getPost_num();
    }*/



    @Transactional

    public void deletePost(String postNum) {
        postRepository.deleteById(Integer.valueOf(postNum));
    }


    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        //리스트 내림차순
        Collections.reverse(postEntityList);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        System.out.println("postDTOList = " + postDTOList);
        return postDTOList;
    }


    // 검색기능 //
    @Transactional
    public Page<PostDTO> search(String keyword, Pageable pageable) {
        /*        List<PostEntity> postEntityList = postRepository.findByPostTitleContaining(keyword); */

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 12;

        List<PostDTO> postList = new ArrayList<>();

        Page<PostEntity> postEntityList = postRepository.findByPostTitleContainingOrPostContentContaining(keyword, keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "postRegdate")));

        Page<PostDTO> postDTOS = postEntityList.map(post -> PostDTO.from(post));

        return postDTOS;
    }

    // 판매내역
    @Transactional
    public Page<PostDTO> findByUserNum(Integer userNum, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 12;

        List<PostDTO> postDTOList = new ArrayList<>();
        Page<PostEntity> postEntities = postRepository.findByUserEntityUserNum(userNum, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "postNum")));
        Page<PostDTO> postDTOS = postEntities.map(post -> PostDTO.from(post));

        return postDTOS;
    }

    @Transactional
    public Page<PostDTO> findByCategoryNum(Integer categoryNum, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 12;

        List<PostDTO> postDTOList = new ArrayList<>();
        Page<PostEntity> postEntities = postRepository.findByCategoryEntityCategoryNum(categoryNum, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "postNum")));
        Page<PostDTO> postDTOS = postEntities.map(post -> PostDTO.from(post));

        return postDTOS;
    }


/////////////////////////////////// 조회수 //////////////////////////////////////

    //ip기준으로 증가시키기
    public String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Transactional
    public void increasePostHits(Integer postNum) {
        // 클라이언트의 IP 주소를 가져옴
        String clientIp = getClientIp(request);

        // 해당 IP 주소로 조회한 게시물 번호를 가져옴
        Set<Integer> viewedPosts = (Set<Integer>) request.getSession().getAttribute(clientIp);
        if (viewedPosts == null) {
            viewedPosts = new HashSet<>();
            request.getSession().setAttribute(clientIp, viewedPosts);
        }

        // 해당 IP 주소로 조회한 게시물 번호가 없으면 조회수 증가 처리 후 세션에 저장
        if (!viewedPosts.contains(postNum)) {
            PostEntity postEntity = postRepository.findById(postNum).orElse(null);
            if (postEntity != null) {
                int currentHits = postEntity.getPostHits();
                postEntity.setPostHits(currentHits + 1);
                postRepository.save(postEntity);
            }
            // 해당 IP 주소로 조회한 게시물 번호를 세션에 저장하여 중복 조회 방지
            viewedPosts.add(postNum);
            request.getSession().setAttribute(clientIp, viewedPosts);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////


    @Transactional(readOnly = true)
    public List<PostDTO> findPostsByPostNums(List<Integer> postNums) {
        List<PostEntity> postEntityList = postRepository.findAllById(postNums);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    public int findFirstPostNum() {
        PostEntity firstPost = postRepository.findFirstByOrderByPostNumDesc();
        return firstPost != null ? firstPost.getPostNum() : -1;
    }


}

