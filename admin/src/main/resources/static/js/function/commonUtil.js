/*
* 字段列时间格式化'yyyy-mm-dd : HH:mm:ss'
* */
function dateFormatter (value, row, index){
    // console.log(value + row + index);
    debugger;
    if (value != null) {
        var date = new Date(value);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate()  < 10 ? "0" + (date.getDate()) : (date.getDate());

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
    return value;
}