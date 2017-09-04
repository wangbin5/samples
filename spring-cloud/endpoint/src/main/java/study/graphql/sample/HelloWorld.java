package study.graphql.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.language.ObjectTypeDefinition;
import graphql.language.TypeDefinition;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.io.ClassPathResource;
import study.graphql.sample.schema.CreateByFile;
import study.graphql.sample.schema.CreateByProgram;
import study.graphql.sample.schema.SchemaCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Administrator on 2017/7/14.
 */
public class HelloWorld {

    public static void main(String[] args) throws IOException {
        boolean defineByProgram = true;
        SchemaCreator schemaCreator = defineByProgram ? new CreateByProgram() : new CreateByFile();

        GraphQLSchema schema = schemaCreator.create();


        String query1 = "{users(page:2,size:5,name:\"15\") {id,sex,name,pic}}";
        String query2 = "{user(id:6) {id,sex,name,pic}}";
        String query3 = "{user(id:6) {id,sex,name,pic},users(page:2,size:5,name:\"15\") {id,sex,name,pic}}";

        Map<String, Object> result1 = (Map<String, Object>) new GraphQL(schema).execute(query1).getData();
        Map<String, Object> result2 = (Map<String, Object>) new GraphQL(schema).execute(query2).getData();
        Map<String, Object> result3 = (Map<String, Object>) new GraphQL(schema).execute(query3).getData();

        // 查询用户列表
        System.out.println(new ObjectMapper().writeValueAsString(result1));
        // 查询单个用户
        System.out.println(new ObjectMapper().writeValueAsString(result2));
        // 单个用户、跟用户列表一起查
        System.out.println(new ObjectMapper().writeValueAsString(result3));
    }
}
