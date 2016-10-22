package eric.clapton.musician.core.entity.dto.tweet;

import java.io.Serializable;

public class Mention implements Serializable {
    private static final long serialVersionUID = -2751787687074950582L;

    private Long id;
    private String userName;
    private String nickName;

    public Mention() {

    }

    public Mention(Long id, String userName) {
        this(id, userName, null);
    }

    public Mention(Long id, String userName, String nickName) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
