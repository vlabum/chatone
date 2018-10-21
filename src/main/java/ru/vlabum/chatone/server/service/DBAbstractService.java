package ru.vlabum.chatone.server.service;

import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;

@Getter
@ApplicationScoped
abstract class DBAbstractService {

    private static final String RESOURCE = "mybatis-config.xml";

    final SqlSessionFactory sqlSessionFactory;

    final SqlSession sqlSession;

    DBAbstractService() throws IOException {
        final InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

}
