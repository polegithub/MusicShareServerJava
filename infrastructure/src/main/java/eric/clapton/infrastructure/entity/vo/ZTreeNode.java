package eric.clapton.infrastructure.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZTreeNode implements Serializable {
    private static final long serialVersionUID = 7067472342121171333L;

    private boolean checked;
    private final List<ZTreeNode> children;
    private boolean chkDisabled;
    private boolean halfCheck;
    private String icon;
    private String iconClose;
    private String iconOpen;
    private String iconSkin;
    @JsonProperty("isParent")
    private boolean isParent;
    private String name;
    private boolean nocheck;
    private String target;
    private String url;
    private boolean open;
    private Map<String, Serializable> customData;

    public ZTreeNode() {
        this(null);
    }

    public ZTreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<ZTreeNode>();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(boolean halfCheck) {
        this.halfCheck = halfCheck;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Map<String, Serializable> getCustomData() {
        return customData;
    }

    public ZTreeNode addCustomData(String name, Serializable value) {
        if (customData == null) {
            customData = new HashMap<String, Serializable>();
        }

        customData.put(name, value);
        return this;
    }

    public List<ZTreeNode> getChildren() {
        return children;
    }

    public final ZTreeNode addChildNode(ZTreeNode child) {
        children.add(child);
        return this;
    }

}
