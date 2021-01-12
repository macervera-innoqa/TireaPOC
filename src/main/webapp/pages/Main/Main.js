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

Page.graficaFechaFin = function() {
    let todayObj = new Date();
    let todayDay = String(todayObj.getDate()).padStart(2, '0');
    let todayMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
    let todayYear = todayObj.getFullYear();
    return todayDay + "/" + todayMonth + "/" + todayYear;
};

Page.graficaFechaIni = function() {
    let todayObj = new Date();
    let todayDay = String(todayObj.getDate()).padStart(2, '0');
    let todayMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
    let todayYear = todayObj.getFullYear();
    let lastDay = String(todayObj.getDate()).padStart(2, '0');
    let lastMonth = String(todayObj.getMonth()).padStart(2, '0');
    let lastYear = todayObj.getFullYear() - 2;
    return lastDay + "/" + lastMonth + "/" + lastYear;
};