package News.Service.Repositories;

import News.Service.DTO.PostView.PostPreviewResponse;
import News.Service.Models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    @Query(
            value = "SELECT new News.Service.DTO.PostView.PostPreviewResponse(" +
                    "n.id," +
                    " n.title," +
                    " c.name," +
                    " n.authorName," +
                    " n.imageUrl," +
                    " n.createdAt) " +
                    "FROM Post n JOIN n.category c",

            // Подсказываем Спрингу, как считать общее количество записей:
            countQuery = "SELECT COUNT(n) FROM Post n JOIN n.category c"
    )
    Page<PostPreviewResponse> findAllPostsPreview(Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.authorName = :newName WHERE p.authorId = :authorId")
     int updateAuthorName(@Param("authorId") UUID authorId, @Param("newName") String newName);

}
