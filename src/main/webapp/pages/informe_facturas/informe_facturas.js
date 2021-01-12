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
    Page.RecalcularFechas("Último año");
};
Page.advancedFilterButtonClick = function($event, widget) {
    var filterShoed = Page.Widgets.FacturasFilter.show;
    Page.Widgets.FacturasFilter.show = !filterShoed;
    Page.Widgets.advancedFilterButton.caption = (filterShoed ? "Mostrar Filtros" : "Ocultar Filtros");
};
Page.filterButtonClick = function($event, widget) {
    var filterNumber = 0;
    if (Page.Widgets.FacturasFilter.filterWidgets.fechaFactura.minValue !== "" ||
        Page.Widgets.FacturasFilter.filterWidgets.fechaFactura.maxValue !== "")
        filterNumber++;

    if (Page.Widgets.FacturasFilter.filterWidgets.idCliente.datavalue !== undefined &&
        Page.Widgets.FacturasFilter.filterWidgets.idCliente.datavalue !== "")
        filterNumber++;

    if (Page.Widgets.FacturasFilter.filterWidgets.idServicio.datavalue !== undefined &&
        Page.Widgets.FacturasFilter.filterWidgets.idServicio.datavalue !== "")
        filterNumber++;

    Page.Widgets.advancedFilterButton.badgevalue = filterNumber;
    Page.Widgets.FacturasFilter.filter();
};
Page.clearButtonClick = function($event, widget) {
    Page.Widgets.FacturasFilter.filterWidgets.fechaFactura.minValue = "";
    Page.Widgets.FacturasFilter.filterWidgets.fechaFactura.maxValue = "";
    Page.Widgets.FacturasFilter.filterWidgets.idCliente.datavalue = undefined;
    Page.Widgets.FacturasFilter.filterWidgets.idServicio.datavalue = undefined;
    Page.Widgets.advancedFilterButton.badgevalue = 0;
    Page.Widgets.FacturasFilter.filter();
};

Page.RecalcularFechas = function(newVal) {
    //Calcular la fecha de hoy
    let todayObj = new Date();
    let todayDay = String(todayObj.getDate()).padStart(2, '0');
    let todayMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
    let todayYear = todayObj.getFullYear();
    let lastDay = String(todayObj.getDate()).padStart(2, '0');
    let lastMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
    let lastYear = todayObj.getFullYear() - 1;

    switch (newVal) {
        case "Últimos dos años":
            lastDay = String(todayObj.getDate()).padStart(2, '0');
            lastMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
            lastYear = todayObj.getFullYear() - 2;
            break;
        case "Últimos 6 meses":
            todayObj.setDate(todayObj.getMonth() - 5);
            lastDay = String(todayObj.getDate()).padStart(2, '0');
            lastMonth = String(todayObj.getMonth() + 1).padStart(2, '0');
            lastYear = todayObj.getFullYear();
            break;
    }
    let endDate = todayDay + "/" + todayMonth + "/" + todayYear + " 23:59:59";
    let iniDate = lastDay + "/" + lastMonth + "/" + lastYear + " 00:00:00";
    Page.Variables.facturaGraphVariable.setInput("fechaIni", iniDate);
    Page.Variables.facturaGraphVariable.setInput("fechaFin", endDate);
    Page.Variables.facturaGraphVariable.invoke();

};
Page.DateSelectionSwitchChange = function($event, widget, newVal, oldVal) {
    Page.RecalcularFechas(newVal);
};
Page.select1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.facturaGraphVariable.setInput("idCliente", newVal);
    Page.Variables.facturaGraphVariable.invoke();
};
