module.exports = function(grunt) {
  grunt.initConfig({
    watch: {
      sass: {
        files: ['resources/scss/**/*.scss'],
        tasks: ['sass:dist']
      },
      livereload: {
        files: ['resources/scss/**/*.scss'],
        options: { livereload: true }
      }
    },
    sass: {
      options: { sourceMap: true },
      dist: {
        files: {
          'resources/public/css/main.css': 'resources/scss/main.scss'
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('default', ['watch:sass']);
}
