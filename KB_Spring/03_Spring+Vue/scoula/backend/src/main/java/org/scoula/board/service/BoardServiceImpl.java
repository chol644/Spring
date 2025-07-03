package org.scoula.board.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.scoula.common.pagination.Page;
import org.scoula.common.pagination.PageRequest;
import org.scoula.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Log4j2                      // 로깅
@Service                     // Service 계층 컴포넌트
@RequiredArgsConstructor     // final 필드 생성자 주입
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;  // Mapper 의존성 주입

    // 파일 저장될 디렉토리 경로
    private final static String BASE_DIR = "c:/upload/board";

    // 목록 조회 서비스
    @Override
    public List<BoardDTO> getList() {
        log.info("getList..........");

        return boardMapper.getList().stream()    // List<BoardVO> → Stream<BoardVO>
                .map(BoardDTO::of)               // Stream<BoardVO> → Stream<BoardDTO>
                .toList();                       // Stream<BoardDTO> → List<BoardDTO>
    }

    // 단일 조회 서비스
    @Override
    public BoardDTO get(Long no) {
        log.info("get......" + no);
        BoardDTO board = BoardDTO.of(boardMapper.get(no));
        log.info("========================" + board);
        return Optional.ofNullable(board)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional // 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    @Override
    public BoardDTO create(BoardDTO board) {
        log.info("create......" + board);
        BoardVO boardVO = board.toVo();
        boardMapper.create(boardVO);
// 파일 업로드 처리
        List<MultipartFile> files = board.getFiles();
        if (files != null && !files.isEmpty()) {
            upload(boardVO.getNo(), files);
        }
        return get(boardVO.getNo());
    }


    @Override
    public BoardDTO update(BoardDTO board) {
        log.info("update...... " + board);
        BoardVO boardVO = board.toVo();
        log.info("update...... " + boardVO);
        boardMapper.update(boardVO);
        // 파일 업로드 처리
        List<MultipartFile> files = board.getFiles();
        if (files != null && !files.isEmpty()) {
            upload(board.getNo(), files);
        }
        return get(board.getNo());
    }

    @Override
    public BoardDTO delete(Long no) {
        log.info("delete...." + no);
        BoardDTO board = get(no);
        boardMapper.delete(no);
        return board;
    }

    /* 파일 첨부 관련 메서드 추가 */

    // 첨부파일 단일 조회
    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return boardMapper.getAttachment(no);
    }

    // 첨부파일 삭제
    @Override
    public boolean deleteAttachment(Long no) {
        return boardMapper.deleteAttachment(no) == 1;
    }

    /**
     * 파일 업로드 처리 (private 메서드)
     *
     * @param bno   게시글 번호
     * @param files 업로드할 파일 목록
     */
    private void upload(Long bno, List<MultipartFile> files) {
        for (MultipartFile part : files) {
            if (part.isEmpty()) continue;
            try {
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
                boardMapper.createAttachment(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);
//log.error(e.getMessage());
            }
        }
    }
    @Override
    public Page<BoardDTO> getPage(PageRequest pageRequest) {
        List<BoardVO> boards = boardMapper.getPage(pageRequest);
        int totalCount = boardMapper.getTotalCount();
        return Page.of(pageRequest, totalCount,
                boards.stream().map(BoardDTO::of).toList());
    }
}