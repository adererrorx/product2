package com.kh.product.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  //등록
  @Test
  @DisplayName("상품등록")
  void save(){
    Product product = new Product();
    product.setPname("컴퓨터");
    product.setQuantity(2L);
    product.setPrice(2000000L);

    Long productId = productDAO.save(product);
    log.info("productId={}",productId);
    Assertions.assertThat(productId).isGreaterThan(0L);
  }

  //조회
  @Test
  @DisplayName("상품조회")
  void findById(){
    Long productId = 43L;
    Optional<Product> product = productDAO.findById(productId);
    Product findedProduct = product.orElseThrow();// 없으면 java.util.NoSuchElementException
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("컴퓨터");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(2L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(2000000L);
  }

  //수정
  @Test
  @DisplayName("상품수정")
  void update() {
    Long productId = 43L;
    Product product = new Product();
    product.setPname("컴퓨터_수정");
    product.setQuantity(3L);
    product.setPrice(2500000L);
    int updatedRowCount = productDAO.update(productId, product);
    Optional<Product> findedProduct = productDAO.findById(productId);

    Assertions.assertThat(updatedRowCount).isEqualTo(1);
    Assertions.assertThat(findedProduct.get().getPname()).isEqualTo(product.getPname());
    Assertions.assertThat(findedProduct.get().getQuantity()).isEqualTo(product.getQuantity());
    Assertions.assertThat(findedProduct.get().getPrice()).isEqualTo(product.getPrice());
  }

  //삭제
  @Test
  @DisplayName("상품삭제")
  void delete() {
    Long productId = 43L;
    int deletedRowCount = productDAO.delete(productId);
    Optional<Product> findedProduct = productDAO.findById(productId);
    Assertions.assertThatThrownBy(()->findedProduct.orElseThrow())
        .isInstanceOf(NoSuchElementException.class);
  }

  //목록
  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Product> list = productDAO.findAll();
    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("상품존재")
  void isExist(){
    Long prodocutId = 244L;
    boolean exist = productDAO.isExist(prodocutId);
    Assertions.assertThat(exist).isTrue();
  }
  @Test
  @DisplayName("상품무")
  void isExist2(){
    Long prodocutId = 0L;
    boolean exist = productDAO.isExist(prodocutId);
    Assertions.assertThat(exist).isFalse();
  }

  @Test
  @DisplayName("전체 삭제")
  void deleteAll(){
    int deletedRows = productDAO.deleteAll();
    int countOfRecord = productDAO.countOfRecord();
    Assertions.assertThat(countOfRecord).isEqualTo(0);
  }

  @Test
  @DisplayName("레코드 건수")
  void countOfRecord(){
    int countOfRecord = productDAO.countOfRecord();
    log.info("countOfRecord={}",countOfRecord);
  }


  @Test
  @DisplayName("부분삭제")
  void deleteParts(){
    List<Long> productIds = Arrays.asList();
    int rows = productDAO.deleteParts(productIds);
    Assertions.assertThat(rows).isEqualTo(productIds.size());
  }
}

