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