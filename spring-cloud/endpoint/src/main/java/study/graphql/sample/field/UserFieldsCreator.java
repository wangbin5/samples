package study.graphql.sample.field;

import graphql.schema.*;
import study.graphql.sample.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;

/**
 * Created by Administrator on 2017/7/14.
 */
public class UserFieldsCreator {
    private GraphQLOutputType userType;
    private Map<Integer,User> users = new HashMap<>();
    private void initData(){
        for(int i=0;i<100;i++){
            users.put(i,createUser(i));
        }
    }
    private User createUser(int id) {
        User user = new User();
        user.setId(id);
        user.setAge(id + 15);
        user.setSex(id % 2);
        user.setName("Name_" + id);
        user.setPic("pic_" + id + ".jpg");
        return user;
    }

    public UserFieldsCreator(){
        initData();
    }

    public DataFetcher createUsersDataFetcher(){
        return new DataFetcher<List<User>>(){
            @Override
            public  List<User> get(DataFetchingEnvironment environment) {
                int page = environment.getArgument("page");
                int size = environment.getArgument("size");
                String name = environment.getArgument("name");
                // 执行查询, 这里随便用一些测试数据来说明问题
                List<User> list = new ArrayList<>(size);
                for(Map.Entry<Integer,User> entry : users.entrySet()){
                    if(entry.getValue().getName().indexOf(name)!=-1){
                        list.add(entry.getValue());
                    }
                }
                return list;
            }
        };
    }

    public DataFetcher createUserDataFetcher(){
        return new DataFetcher<User>(){

            @Override
            public User get(DataFetchingEnvironment environment) {
                int id = environment.getArgument("id");
                return users.get(id);
            }
        };
    }



}
