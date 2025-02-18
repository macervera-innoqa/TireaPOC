/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
};
Page.seePasswordClick = function($event, widget) {
    if (Page.Widgets.form1.formWidgets.j_password.type === 'password') {
        Page.Widgets.form1.formWidgets.j_password.type = 'text';
        Page.Widgets.seePassword.iconclass = 'fa fa-eye';
    } else {
        Page.Widgets.form1.formWidgets.j_password.type = 'password';
        Page.Widgets.seePassword.iconclass = 'fa fa-eye-slash';
    }
};