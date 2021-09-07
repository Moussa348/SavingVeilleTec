import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-ambient-music',
  templateUrl: './ambient-music.component.html',
  styleUrls: ['./ambient-music.component.css','./ambient-music.component.scss']
})
export class AmbientMusicComponent implements OnInit {
  audioSrc = "./assets/audio/bgSound1.mp3";
  currentVolume = 100;
  volumeBeforeMutting = 0;

  formatLabel(value: number) {
    console.log(value);
    this.currentVolume = value;
      return value + '%';

  }

  constructor(
    private elRef: ElementRef,
    private renderer : Renderer2
  ) { }

  ngOnInit(): void {
  }

  volumeControl($event){
    console.log($event.target.value);
  }

  muteUnMute(){
    if(this.isVolumeMute()){
      this.currentVolume = this.volumeBeforeMutting;
    }else{
      this.volumeBeforeMutting = this.currentVolume;
      this.currentVolume = 0;
    }

  }

  isVolumeMute(){
    return this.currentVolume == 0;
  }




}
