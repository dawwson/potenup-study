package com.ohgiraffers.mission.a_basic;

import com.ohgiraffers.chap01.section03.model.Order;
import com.ohgiraffers.chap01.section04.model.Customer;
import com.ohgiraffers.chap01.section04.model.Delivery;
import com.ohgiraffers.mission.a_basic.model.Comment;
import com.ohgiraffers.mission.a_basic.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Post post = new Post("새로운 게시글");
            em.persist(post);

            Comment comment1 = new Comment("댓글 1", post);
            Comment comment2 = new Comment("댓글 2", post);
            Comment comment3 = new Comment("댓글 3", post);

            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);

            tx.commit();
            em.clear();

            Post foundPost = em.find(Post.class, post.getId());
            System.out.println(foundPost);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
