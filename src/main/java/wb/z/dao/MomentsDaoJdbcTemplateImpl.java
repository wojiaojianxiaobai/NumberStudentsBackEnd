package wb.z.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import wb.z.bean.MomentsItem;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MomentsDaoJdbcTemplateImpl implements MomentsDao {

    @Autowired
    private NamedParameterJdbcTemplate mMomentsDaoJdbcTemplate;

    @Override
    public int addMomentsItem(MomentsItem momentsItem) {
        String sql = "insert into t_moments(moment_id,moment_picture_path,user_name," +
                "user_nick_name,moment_time,moment_title, " +
                "moment_content) " +
                "values(:momentId,:momentPicturePath,:userName," +
                ":userNickName,:momentTime,:momentTitle," +
                ":momentContent)";
        Map<String, Object> param = new HashMap<>();
        param.put("momentId", momentsItem.getMomentId());
        param.put("momentPicturePath", momentsItem.getMomentPicturePath());
        param.put("userName", momentsItem.getUserName());
        param.put("userNickName",momentsItem.getUserNickName());
        param.put("momentTime",momentsItem.getMomentTime());
        param.put("momentTitle",momentsItem.getMomentTitle());
        param.put("momentContent",momentsItem.getMomentContent());

        System.out.print("\n ----------  " + param.toString() + "  --------------\n ");

//        String sql = "insert into t_moments VALUES "
//                + " moment_id = " + momentsItem.getMomentId() + ","
//                + "moment_picture_path = " + "'"+ momentsItem.getMomentPicturePath()+ "'" + ","
//                + "user_name = "+ "'" + momentsItem.getUserName()+ "'" + ","
//                + "user_nick_name = "+ "'" + momentsItem.getUserNickName()+ "'" + ","
//                + "moment_time = " + "'" + momentsItem.getMomentTime() + "'"+ ","
//                + "moment_title = " + "'"+ momentsItem.getMomentTitle()+ "'" + ","
//                + "momentContent = "+ "'" + momentsItem.getMomentContent()+ "'";



        mMomentsDaoJdbcTemplate.update(sql,param);


        return 0;
    }

    @Override
    public int deleteMomentsItem() {
        return 0;
    }
}
