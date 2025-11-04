-- jpa_lecture 데이터베이스를 사용한다.
USE JPA_LECTURE;

-- 기존 테이블이 존재하면 삭제한다.
DROP TABLE IF EXISTS tbl_product;

-- Product 엔티티에 매핑될 테이블을 생성한다.
CREATE TABLE tbl_product (
                             product_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 ID',
                             product_name VARCHAR(255) NOT NULL COMMENT '상품명',
                             price INT NOT NULL COMMENT '가격'
) COMMENT 'Spring Data JPA 실습용 상품 테이블';

-- 쿼리 메소드 및 @Query 실습을 위한 샘플 데이터를 삽입한다.
INSERT INTO tbl_product (product_name, price) VALUES
                                                  ('아메리카노', 4500),
                                                  ('카페라떼', 5000),
                                                  ('바닐라라떼', 5500),
                                                  ('카푸치노', 5000),
                                                  ('카라멜 마끼아또', 5800),
                                                  ('에스프레소', 4000),
                                                  ('녹차라떼', 5200),
                                                  ('딸기 스무디', 6000),
                                                  ('치즈케이크', 6500),
                                                  ('초콜릿 케이크', 6500);

-- 데이터 삽입 확인
SELECT * FROM tbl_product;