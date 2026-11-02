import AVFoundation
import Foundation

let input = URL(fileURLWithPath: "/Users/mh/Downloads/정보처리기사 실기 공부법 벼락치기 합격 방법.mp4")
let asset = AVURLAsset(url: input)
let duration = try await asset.load(.duration)
let tracks = try await asset.loadTracks(withMediaType: .video)
let audioTracks = try await asset.loadTracks(withMediaType: .audio)
print("duration=\(CMTimeGetSeconds(duration))")
print("videoTracks=\(tracks.count) audioTracks=\(audioTracks.count)")
if let track = tracks.first {
    let size = try await track.load(.naturalSize)
    let transform = try await track.load(.preferredTransform)
    print("size=\(size.width)x\(size.height), transform=\(transform)")
}
