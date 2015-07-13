module.exports = function (grunt) {
    var srcDir = 'src/main/';
    var lessDir = srcDir + 'less/';
    var jsDir = srcDir + 'js/';
    var imgDir = srcDir + 'images/';

    var bowerDir = 'bower_components/';

    var dstDir = 'target-grunt/';
    var dstFontsDir = dstDir + 'resources/fonts/';
    var dstCssDir = dstDir + 'resources/css/';
    var dstJsDir = dstDir + 'resources/js/';
    var dstImgDir = dstDir + 'resources/images/';

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        clean: [dstDir],

        watch: {
            files: [
                jsDir + '**',
                lessDir + '**',
                imgDir + '**'
            ],
            tasks: 'dev'
        },

        copy: {
            fontawesome_fonts: {
                expand: true,
                cwd: bowerDir + 'fontawesome/fonts',
                src: ['*'],
                dest: dstFontsDir
            },
            images: {
                expand: true,
                cwd: imgDir,
                src: ['**'],
                dest: dstImgDir
            }
        },

        uglify: {
            dist: {
                options: {
                    compress: {
                        warnings: true
                    },
                    report: 'min'
                },
                src: [
                    bowerDir + 'angular/angular.js',
                    bowerDir + 'angular-bootstrap/ui-bootstrap.js',
                    bowerDir + 'angular-bootstrap/ui-bootstrap-tpls.js',
                    bowerDir + 'highlightjs/highlight.pack.js',
                    jsDir + '*.js',
                    jsDir + '**/*.js'
                ],
                dest: dstJsDir + 'all.js'
            },
            dev: {
                options: {
                    beautify: true,
                    compress: false,
                    report: 'min'
                },
                src: '<%= uglify.dist.src %>',
                dest: '<%= uglify.dist.dest %>'
            }
        },

        less: {
            dist: {
                options: {
                    compress: true,
                    yuicompress: true,
                    cleancss: true,
                    report: 'min'
                },
                src: [
                    bowerDir + 'bootstrap/dist/css/bootstrap.css',
                    bowerDir + 'fontawesome/css/font-awesome.css',
                    bowerDir + 'highlightjs/styles/github.css',
                    lessDir + '**/*.less',
                    lessDir + '*.less'
                ],
                dest: dstCssDir + 'all.css'
            },
            dev: {
                options: {
                    compress: true,
                    yuicompress: true,
                    report: 'min'
                },
                src: '<%= less.dist.src %>',
                dest: '<%= less.dist.dest %>'
            }
        },

        compress: {
            dist: {
                options: {
                    mode: 'gzip'
                },
                files: [
                    {expand: true, cwd: dstJsDir, src: ['*.js', '**/*.js'], dest: dstJsDir, ext: '.js.gz'},
                    {expand: true, cwd: dstCssDir, src: ['*.css', '**/*.css'], dest: dstCssDir, ext: '.css.gz'}
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-compress');

    grunt.registerTask('default', ['uglify:dist', 'less:dist', 'copy', 'compress']);
    grunt.registerTask('dev', ['uglify:dev', 'less:dev', 'copy']);

};