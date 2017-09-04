package study.graphql.sample.schema;

import graphql.language.TypeDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.io.ClassPathResource;
import study.graphql.sample.field.UserFieldsCreator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/14.
 */
public class CreateByFile implements  SchemaCreator{
    private UserFieldsCreator userFieldsCreator=new UserFieldsCreator();
    @Override
    public GraphQLSchema create() {
        try {
            SchemaParser schemaParser = new SchemaParser();
            SchemaGenerator schemaGenerator = new SchemaGenerator();

            File schemaFile = new ClassPathResource("graphqls/user.graphqls").getFile();
            TypeDefinitionRegistry typeRegistry = schemaParser.parse(schemaFile);
            RuntimeWiring wiring = buildRuntimeWiring();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
            return graphQLSchema;

        }
        catch(IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                // 自定义类型
                // .scalar(CustomScalar)
                // this uses builder function lambda syntax
                .type("QueryType", typeWiring -> typeWiring
                        .dataFetcher("users", userFieldsCreator.createUsersDataFetcher())
                        .dataFetcher("user", userFieldsCreator.createUserDataFetcher())
                )
                .build();
    }
}
