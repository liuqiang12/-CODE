/**
 * Created by Victor on 2016/8/11.
 */
require.config({
    paths: {
        JTopo: "core/jtopo/jtopo-min",
        template: 'core/template/html',
        controller: 'core/js/controller',
        element: 'core/js/element',
        tool: 'core/js/tool',
        window: 'core/js/window'
    },
    waitSeconds: 0,
    baseUrl: './',
    urlArgs: "r=" + (new Date()).getTime(),
    shim: {
        controller: {
            deps: ['JTopo']
        },
        element: {
            deps: ['JTopo']
        },
        tool: {
            deps: ['JTopo', 'bootstrap']
        },
        window: {
            deps: ['bootstrap']
        }
    },
    map: {
        '*': {
            'css': 'resource/js/require/plugins/css.min',
            'text': 'resource/js/require/plugins/text'
        }
    }
});
require(['element/Scene', 'controller/action', 'JTopo'],
    function (Scene, action) {
        if (typeof jQuery === 'undefined') {
            throw new Error('topo_base\'s JavaScript requires jQuery')
        }
        $(document).ready(function () {
            var canvas = document.getElementById('topo_base');
            canvas.setAttribute('width', $(window).width());
            canvas.setAttribute('height', $(window).height());
            $(window).resize(function () {
                canvas.setAttribute('width', $(window).width());
                canvas.setAttribute('height', $(window).height());
            });
            $.ajax({
                url: 'topo.config',
                type:'get',
                success: function (config) {
                    console.log('___________________________________')
                    config = JSON.parse(config);
                    var stage = new JTopo.Stage(canvas);
                    var scene = new Scene();
                    scene.prop.config = config;
                    if(!config.background){
                        console.info("not config background");
                    }else{
                        scene.background = config.background;
                    }
                    stage.add(scene);
                    action.addSceneListener(scene);
                    action.loadTopo(stage, scene);
                },
                error: function (e) {
                    console.error("need topo.config");
                }
            });

        })
    });