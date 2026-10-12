import { Track } from 'livekit-client';
import { type IMediaTrack } from '@/types/room/track/IMediaTrack';

export class AudioTrack implements IMediaTrack {
    private _hasTrack: boolean = false;
    private _track: Track | null = null;
    private _isSpeaking: boolean = false;

    get hasTrack(): boolean {
        return this._hasTrack;
    }

    get track(): Track | null {
        return this._track;
    }

    get isSpeaking(): boolean {
        return this._isSpeaking;
    }

    addTrack(track: Track): void {
        if (this._hasTrack) {
            console.log(`нельзя добавить более одного аудио потока`);
            return;
        }

        if (track.kind !== Track.Kind.Audio) {
            console.warn(`Попытка добавить не-аудио трек в AudioTrack: ${track.kind}`);
            return;
        }

        this._hasTrack = true;
        this._track = track;
        console.log('Аудио трек добавлен');
    }

    deleteTrack(): void {
        this._track = null;
        this._hasTrack = false;
        console.log('Аудио трек удалён');
    }

    setIsSpeaking(isSpeaking: boolean): void {
        this._isSpeaking = isSpeaking;
    }
}