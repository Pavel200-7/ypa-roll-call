import { ref, type Ref } from 'vue';
import {
  Track,
} from 'livekit-client';
import { ParticipantIdentity } from '@/types/room/participant/ParticipantIdentity';
import { AudioTrack } from '@/types/room/track/AudioTrack';
import { VideoTrack } from '@/types/room/track/VideoTrack';

interface ParticipantMetadata {
  role?: 'admin' | 'participant'
}

export class ParticipantInfo {
  private _identity: ParticipantIdentity;
  private _isLocal: boolean;
  private _isAdmin: boolean;
  private _audioTrack: AudioTrack;
  private _videoTrack: VideoTrack;

  private _changeTracker: Ref<number>;

  constructor(identity: string, isLocal: boolean, metadata: string | null) {
    this._identity = new ParticipantIdentity(identity);
    this._isLocal = isLocal;
    this._isAdmin = this.parseAdmin(metadata);
    this._audioTrack = new AudioTrack();
    this._videoTrack = new VideoTrack();

    this._changeTracker = ref(0);
  }

  private parseAdmin(metadata?: string | null): boolean {
    if (!metadata) return false
    try {
      const parsed = JSON.parse(metadata) as ParticipantMetadata
      return parsed.role === 'admin'
    } catch {
      return false
    }
  }

  private emitChange(): void {
    this._changeTracker.value++;
  }

  private ensureReactive(): void {
    this._changeTracker.value;
  }

  // --- МЕТОДЫ-ГЕТТЕРЫ ---

  getIdentity(): ParticipantIdentity {
    return this._identity;
  }

  getIdentityString(): string {
    return this._identity.identity;
  }

  getShortIdentity(): string {
    return this._identity.getShortIdentity();
  }

  isLocal(): boolean {
    return this._isLocal;
  }

  getAudioTrack(): AudioTrack {
    return this._audioTrack;
  }

  getVideoTrack(): VideoTrack {
    return this._videoTrack;
  }

  hasAudio(): boolean {
    return this._audioTrack.hasTrack;
  }

  hasVideo(): boolean {
    return this._videoTrack.hasTrack;
  }

  isSpeaking(): boolean {
    return this._audioTrack.isSpeaking;
  }

  isAdmin(): boolean {
    return this._isAdmin
  }


  // --- РЕАКТИВНЫЙ ГЕТТЕР ---

  get reactive(): {
    getIdentity: () => ParticipantIdentity;
    getIdentityString: () => string;
    getShortIdentity: () => string;
    isLocal: () => boolean;
    getAudioTrack: () => AudioTrack;
    getVideoTrack: () => VideoTrack;
    hasAudio: () => boolean;
    hasVideo: () => boolean;
    isSpeaking: () => boolean;
    isAdmin: () => boolean;
  } {
    this.ensureReactive();

    return {
      getIdentity: this.getIdentity.bind(this),
      getIdentityString: this.getIdentityString.bind(this),
      getShortIdentity: this.getShortIdentity.bind(this),
      isLocal: this.isLocal.bind(this),
      getAudioTrack: this.getAudioTrack.bind(this),
      getVideoTrack: this.getVideoTrack.bind(this),
      hasAudio: this.hasAudio.bind(this),
      hasVideo: this.hasVideo.bind(this),
      isSpeaking: this.isSpeaking.bind(this),
      isAdmin: this.isAdmin.bind(this)

    };
  }

  // --- МЕТОДЫ УПРАВЛЕНИЯ ---

  addTrack(track: Track): void {
    switch (track.kind) {
      case Track.Kind.Audio:
        this._audioTrack.addTrack(track);
        break;
      case Track.Kind.Video:
        this._videoTrack.addTrack(track);
        break;
      default:
        console.warn(`Неизвестный тип потока: ${track.kind}`);
        return;
    }
    this.emitChange();
  }

  removeTrack(kind: Track.Kind): void {
    switch (kind) {
      case Track.Kind.Audio:
        this._audioTrack.deleteTrack();
        break;
      case Track.Kind.Video:
        this._videoTrack.deleteTrack();
        break;
      default:
        console.warn(`Неизвестный тип потока: ${kind}`);
        return;
    }
    this.emitChange();
  }

  setIsSpeaking(isSpeaking: boolean): void {
    this._audioTrack.setIsSpeaking(isSpeaking);
    this.emitChange();
  }

  clearAllTracks(): void {
    this._audioTrack.deleteTrack();
    this._videoTrack.deleteTrack();
    this.emitChange();
  }
}