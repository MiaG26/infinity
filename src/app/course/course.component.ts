import { Component, Input } from '@angular/core';
import { Course } from 'src/entities/course';
import { Video } from 'src/entities/video';
import { Comment } from 'src/entities/comment';
import { CourseService } from 'src/services/course.service';
import { UsersService } from 'src/services/users.service';
import { VideoService } from 'src/services/video.service';
import { CommentService } from 'src/services/comment.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})


export class CourseComponent {

  selectedCourse: Course;
  selectedVideo: Video;
  videos: Video[];
  selectedVideoComments: Observable<Comment[]>;
  addCommentText = '';

  constructor(private usersService: UsersService,
              private courseService: CourseService,
              private videoService: VideoService,
              private commentService: CommentService){}

  ngOnInit(): void {
    this.selectedCourse = this.usersService.getSelectedCourse();
    this.courseService.getCourseVideos(this.selectedCourse.id).subscribe(v => {
      this.videos = v
      this.selectedVideo = this.videos[0];
      this.selectedVideoComments = this.videoService.getVideoComments(this.selectedVideo.id);
    });
  }

  setSelectedVideo(video: Video) {
    this.selectedVideo = video;
    this.selectedVideoComments = this.videoService.getVideoComments(this.selectedVideo.id);
 }

  addComment() {
    if(this.addCommentText.length == 0) {
      return
    }
    let comment = new Comment(this.selectedVideo.id, this.usersService.user.id, this.addCommentText)
    this.commentService.addComment(comment).subscribe();
    this.selectedVideoComments = this.videoService.getVideoComments(this.selectedVideo.id);
    this.addCommentText = ''
  }

}
