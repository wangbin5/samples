package study.graphql.sample.schema;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;
import study.graphql.sample.User;
import study.graphql.sample.field.UserFieldsCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Administrator on 2017/7/14.
 */
public class CreateByProgram implements SchemaCreator{
    private GraphQLSchema schema;

    private UserFieldsCreator userFieldsCreator=new UserFieldsCreator();

    @Override
    public GraphQLSchema create() {
        /**
         * 会员对象结构
         */
        GraphQLOutputType userType = newObject()
                .name("User")
                .field(newFieldDefinition().name("id").type(GraphQLInt).build())
                .field(newFieldDefinition().name("age").type(GraphQLInt).build())
                .field(newFieldDefinition().name("sex").type(GraphQLInt).build())
                .field(newFieldDefinition().name("name").type(GraphQLString).build())
                .field(newFieldDefinition().name("pic").type(GraphQLString).build())
                .build();
        schema = GraphQLSchema.newSchema()
                    .query(
                        newObject().name("GraphQuery")
                            .field(createUsersField(userType))
                            .field(createUserField(userType))
                            .build()
                        )
                    .build();
        return schema;
    }

    public GraphQLFieldDefinition createUsersField(GraphQLOutputType userType) {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("users")
                .argument(newArgument().name("page").type(GraphQLInt).build())
                .argument(newArgument().name("size").type(GraphQLInt).build())
                .argument(newArgument().name("name").type(GraphQLString).build())
                .type(new GraphQLList(userType))
                .dataFetcher(userFieldsCreator.createUsersDataFetcher())
                .build();
    }
    public GraphQLFieldDefinition createUserField(GraphQLOutputType userType) {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("user")
                .argument(newArgument().name("id").type(GraphQLInt).build())
                .type(userType)
                .dataFetcher(userFieldsCreator.createUserDataFetcher())
                .build();
    }


}
