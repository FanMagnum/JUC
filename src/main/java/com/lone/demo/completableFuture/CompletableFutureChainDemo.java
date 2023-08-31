package com.lone.demo.completableFuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureChainDemo  {

    public static void main(String[] args){
        Student student = new Student();
        student.setId(1).setName("lone").setMajor("java");
        log.info("student:{}",student);
    }
}
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
class Student{
    private Integer id;
    private String name;
    private String major;
}
