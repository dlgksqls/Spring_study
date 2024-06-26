package com.example.simpleboard.post.service;

import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.common.Api;
import com.example.simpleboard.common.Pageination;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import com.example.simpleboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final ReplyService replyService;

    public PostEntity create(
            PostRequest postRequest
    ) {
        var boardEntity = boardRepository.findById(postRequest.getBoardId()).get();
        var entity = PostEntity.builder()
                .board(boardEntity)
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        return postRepository.save(entity);
    }

    /**
     *
     * 1. 게시글이 있는가?
     * 2. 비밀번호가 맞는가?
     */
    public PostEntity view(PostViewRequest postViewRequest){
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(), "REGISTERED")
                .map(it->{
                    if (!it.getPassword().equals(postViewRequest.getPassword())){
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

//                    var replyList = replyService.findAllByPostId(it.getId());
//                    it.setReplyList(replyList);

                    return it;
                }).orElseThrow( // 데이터가 없다면
                        ()->{
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다." + postViewRequest.getPostId());
                        }
                );
    }

    public Api<List<PostEntity>> all(Pageable pageable) {
        var list = postRepository.findAll(pageable);

        var pagination = Pageination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        var response = Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pageination(pagination)
                .build();

        return response;
    }

    public void delete(PostViewRequest postViewRequest) {
        postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(), "REGISTERED")
                .map(it->{
                    if (!it.getPassword().equals(postViewRequest.getPassword())){
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

                    it.setStatus("UNREGISTERED");
                    //postRepository.delete(it);
                    postRepository.save(it);
                    return it;
                }).orElseThrow( // 데이터가 없다면
                        ()->{
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다." + postViewRequest.getPostId());
                        }
                );
    }
}
