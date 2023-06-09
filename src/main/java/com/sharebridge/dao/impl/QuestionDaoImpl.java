package com.sharebridge.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sharebridge.dao.QuestionDao;
import com.sharebridge.dto.QuestionDto;
import com.sharebridge.param.QuestionListParam;

@Repository
public class QuestionDaoImpl implements QuestionDao {
	@Autowired
	SqlSession session;

	String ns = "Question.";
	
	@Override
	public int writeQuestion(QuestionDto dto) {
		return session.insert(ns + "writeQuestion", dto);
	}
	
	@Override
	public List<QuestionDto> getQuestionListByProduct_id(QuestionListParam qp) {
		return session.selectList(ns + "getQuestionListByProduct_id", qp);
	}

	@Override
	public int getQuestionCount(int product_id) {
		return session.selectOne(ns + "getQuestionCount", product_id);
	}

	@Override
	public QuestionDto getQuestionByQuestion_id(int question_id) {
		return session.selectOne(ns + "getQuestionByQuestion_id", question_id);
	}

	@Override
	public void updateQstateTo1(int question_id) {
		session.update(ns + "updateQstateTo1", question_id);
	}

	@Override
	public void updateQuestion(QuestionDto questionInfo) {
		session.update(ns + "updateQuestion", questionInfo);
	}

	@Override
	public void deleteQuestion(int question_id) {
		session.delete(ns + "deleteQuestion", question_id);
	}
}
