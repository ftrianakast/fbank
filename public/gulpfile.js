var fs = require('fs');
var gulp = require('gulp');
var gutil = require('gulp-util')
var jade = require('gulp-jade');
var stylus = require('gulp-stylus');

gulp.task('templates', function() {
    return gulp
        .src('src/jade/**/*.jade')
        .pipe(jade({
            pretty: true
        }))
        .pipe(gulp.dest('./'))
        .on('error', function(){
            console.log('There was an error parsing jade to html. Waiting for changes ...');
        });
});

gulp.task('css', function() {
    return gulp
        .src('src/stylus/style.styl')
        .pipe(stylus())
        .pipe(gulp.dest('./'))
        .on('error', function(){
            console.log('There was an error parsing stylus to css. Waiting for changes ...');
        });
});


gulp.task('watch', function() {
    gulp.watch('src/stylus/*.styl', ['css']);
    gulp.watch('src/jade/**/*.jade', ['templates']);
});


gulp.task('develop', ['templates', 'css', 'watch']);