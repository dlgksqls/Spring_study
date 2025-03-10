<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/PostServiceImplTest.java
package com.example.demo.medium;
========
package com.example.demo.post.service;
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/post/service/PostServiceTest.java

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/PostServiceImplTest.java
import com.example.demo.post.service.PostServiceImpl;
========
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/post/service/PostServiceTest.java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceImplTest {

    @Autowired
    private PostServiceImpl postServiceImpl;

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        // given
        // when
<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/PostServiceImplTest.java
        Post result = postServiceImpl.getById(1);
========
        Post result = postService.getById(1);
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/post/service/PostServiceTest.java

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("foobar")
                .build();

        // when
<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/PostServiceImplTest.java
        Post result = postServiceImpl.create(postCreate);
========
        Post result = postService.create(postCreate);
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/post/service/PostServiceTest.java

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("hello world :)")
                .build();

        // when
<<<<<<<< HEAD:Test/test-code-with-architecture-main/src/test/java/com/example/demo/medium/PostServiceImplTest.java
        postServiceImpl.update(1, postUpdate);

        // then
        Post post = postServiceImpl.getById(1);
========
        postService.update(1, postUpdate);

        // then
        Post post = postService.getById(1);
>>>>>>>> origin/main:Test/test-code-with-architecture-main/src/test/java/com/example/demo/post/service/PostServiceTest.java
        assertThat(post.getContent()).isEqualTo("hello world :)");
        assertThat(post.getModifiedAt()).isGreaterThan(0);
    }

}