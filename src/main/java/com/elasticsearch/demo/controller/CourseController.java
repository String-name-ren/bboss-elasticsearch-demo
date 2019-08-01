package com.elasticsearch.demo.controller;


import com.elasticsearch.demo.entity.Course;
import com.elasticsearch.demo.entity.Teacher;
import com.elasticsearch.demo.esmapper.CourseMapper;
import com.elasticsearch.demo.esmapper.TeacherMapper;
import com.elasticsearch.demo.utils.ESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-07-31 16:16
 **/
@RequestMapping("course")
@RestController
public class CourseController {

    @Autowired
    private ESUtil esUtil;

    @GetMapping("createIndex")
    public String createIndex(){
        esUtil.createIndice(CourseMapper.index,CourseMapper.createIndex);
        return "success";
    }


    @GetMapping("create")
    public String create(){
        Course course = new Course();
        course.setCourseId(3L);
        course.setCourseName("共产党党章");
        course.setCreateAt(new Date());
        esUtil.addOrUpdateDocument(CourseMapper.index,CourseMapper.type,course);
        return "success";
    }

    @GetMapping("get/{courseId}")
    public Course get(@PathVariable("courseId") Long courseId){
        return esUtil.getDocumentById(CourseMapper.index,CourseMapper.type, courseId + "", Course.class);
    }

    @GetMapping("getPage")
    public List<Course> getPage(){
        Map<String, Object> params = new HashMap<>();
        params.put("courseId",null);
        params.put("courseName","共产党");
        return esUtil.search(CourseMapper.index,CourseMapper.type,CourseMapper.getPage,params,Course.class);
    }


}
