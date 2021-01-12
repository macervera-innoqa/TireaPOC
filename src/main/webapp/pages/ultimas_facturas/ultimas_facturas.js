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
    Page.RecalcularFechas("Últimos 3 meses");
};

Page.RecalcularFechas = function(newVal) {
    //Calcular la fecha de hoy
    let todayObj = new Date();
    let todayDay = String(todayObj.getDate()).padStart(2, '0');
    let todayMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
    let todayYear = todayObj.getFullYear();
    let lastDay = String(todayObj.getDate()).padStart(2, '0');
    let lastMonth = String(todayObj.getMonth() - 2).padStart(2, '0');
    let lastYear = todayObj.getFullYear();
    //Últimos 6 meses, Últimos 3 meses, Último mes
    switch (newVal) {
        case "Último mes":
            lastDay = String(todayObj.getDate()).padStart(2, '0');
            lastMonth = String(todayObj.getMonth()).padStart(2, '0');
            lastYear = todayObj.getFullYear();
            break;
        case "Últimos 3 años":
            lastDay = String(todayObj.getDate()).padStart(2, '0');
            lastMonth = String(todayObj.getMonth()).padStart(2, '0');
            lastYear = todayObj.getFullYear() - 2;
            break;
    }
    let endDate = todayDay + "-" + todayMonth + "-" + todayYear + " 23:59:59";
    let iniDate = lastDay + "-" + lastMonth + "-" + lastYear + " 00:00:00";
    Page.Variables.facturasVariable.setInput("fechaIni", iniDate);
    Page.Variables.facturasVariable.setInput("fechaFin", endDate);
    Page.Variables.facturasVariable.invoke();

};
Page.DateSelectionSwitchChange = function($event, widget, newVal, oldVal) {
    Page.RecalcularFechas(newVal);
};