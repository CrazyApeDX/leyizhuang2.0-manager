KindEditor.plugin('cutthumbs', function (K) {
    var editor = this, name = 'cutthumbs';
    // 点击图标时执行
    editor.clickToolbar(name, function () {
        //editor.insertHtml('图片裁剪');
        art.dialog.open(editor.pluginsPath + 'cutthumbs/index.html',
        {
            id: 'cutthumbs_win',
            title: '图片裁剪',
            width: '800px',
            height: '70%',
            lock: true
        });
    });
});