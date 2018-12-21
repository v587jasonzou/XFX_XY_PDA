package jx.yunda.com.terminal.modules.main.model;

/**
 * <li>标题：机车整备管理信息系统-手持终端
 * <li>说明：权限菜单
 * <li>创建人：何东
 * <li>创建日期：2017年11月02日
 * <li>修改人;
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权：Copyright (c) 2015 运达科技公司
 * @version 1.0
 */
public class PurviewMenu {
    private String funccode;// 功能点编码
    private String funcaction;// null,
    private String funcname;// 功能点名称
    private MenuConfig menuConfig;

    private TodoJob todoJob;
    
    public String getFunccode() {
        return funccode;
    }
    
    public void setFunccode(String funccode) {
        this.funccode = funccode;
    }
    
    public String getFuncaction() {
        return funcaction;
    }
    
    public void setFuncaction(String funcaction) {
        this.funcaction = funcaction;
    }
    
    public String getFuncname() {
        return funcname;
    }
    
    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public TodoJob getTodoJob() {
        return todoJob;
    }

    public void setTodoJob(TodoJob todoJob) {
        this.todoJob = todoJob;
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    public void setMenuConfig(MenuConfig menuConfig) {
        this.menuConfig = menuConfig;
    }
}
