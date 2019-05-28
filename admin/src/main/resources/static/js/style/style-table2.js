/*
* 行样式，可自行编辑改变行样式
* */
function rowStyle(row, index) {
    //这里有5个取值颜色['active', 'success', 'info', 'warning', 'danger'];
                        var strclass = "";
                         if (index == 0) {
                                 strclass = "success";
                             }
                        return { classes: strclass }
}
