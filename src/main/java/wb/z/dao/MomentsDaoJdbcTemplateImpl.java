package wb.z.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import wb.z.Config.StateConfig;
import wb.z.bean.MomentsItem;
import wb.z.bean.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;


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
        param.put("userNickName", momentsItem.getUserNickName());
        param.put("momentTime", momentsItem.getMomentTime());
        param.put("momentTitle", momentsItem.getMomentTitle());
        param.put("momentContent", momentsItem.getMomentContent());

        System.out.print("\n ----------  " + param.toString() + "  --------------\n ");

//        String sql = "insert into t_moments VALUES "
//                + " moment_id = " + momentsItem.getMomentId() + ","
//                + "moment_picture_path = " + "'"+ momentsItem.getMomentPicturePath()+ "'" + ","
//                + "user_name = "+ "'" + momentsItem.getUserName()+ "'" + ","
//                + "user_nick_name = "+ "'" + momentsItem.getUserNickName()+ "'" + ","
//                + "moment_time = " + "'" + momentsItem.getMomentTime() + "'"+ ","
//                + "moment_title = " + "'"+ momentsItem.getMomentTitle()+ "'" + ","
//                + "momentContent = "+ "'" + momentsItem.getMomentContent()+ "'";


        mMomentsDaoJdbcTemplate.update(sql, param);


        return 0;
    }

    @Override
    public int deleteMomentsItem() {
        return 0;
    }

    @Override
    public String readMoments() {
        String sql = "SELECT * FROM springboot_db.t_moments order by id desc";   //todo 这里先这么写，实际上列表多，应该分段读取
        List<MomentsItem> momentsList = new ArrayList<>();
        momentsList = mMomentsDaoJdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<MomentsItem>(MomentsItem.class));
        if (momentsList.size() < 1) {
            System.out.println("学霸圈为空");
        }
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("[");
        for (int i = 0; i < momentsList.size(); i++) {
            stringBuffer.append(momentsList.get(i).toString());
            if (i != momentsList.size() - 1) {
                stringBuffer.append(",");
            }
        }
//        for (MomentsItem momentsItem : momentsList){
//            stringBuffer.append(momentsItem.toString());
//        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }



}
