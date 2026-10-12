import {
    Track,
} from 'livekit-client';

export interface IMediaTrack {
    hasTrack: boolean;
    track: Track | null;
    addTrack(track: Track): void;
    deleteTrack(): void;
}
