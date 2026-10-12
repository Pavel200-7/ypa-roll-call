import { Track } from 'livekit-client';
import { type IMediaTrack } from '@/types/room/track/IMediaTrack';

export class VideoTrack implements IMediaTrack {
    private _hasTrack: boolean = false;
    private _track: Track | null = null;

    get hasTrack(): boolean {
        return this._hasTrack;
    }

    get track(): Track | null {
        return this._track;
    }

    addTrack(track: Track): void {
        if (this._hasTrack) {
            console.log(`нельзя добавить более одного видео потока`);
            return;
        }

        if (track.kind !== Track.Kind.Video) {
            console.warn(`Попытка добавить не-видео трек в VideoTrack: ${track.kind}`);
            return;
        }

        this._hasTrack = true;
        this._track = track;
        console.log('Видео трек добавлен');
    }

    deleteTrack(): void {
        this._track = null;
        this._hasTrack = false;
        console.log('Видео трек удален');
    }
}