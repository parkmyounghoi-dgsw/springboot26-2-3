import AVFoundation
import AppKit
import Foundation

let input = URL(fileURLWithPath: "/Users/mh/Downloads/정보처리기사 실기 공부법 벼락치기 합격 방법.mp4")
let output = URL(fileURLWithPath: FileManager.default.currentDirectoryPath).appendingPathComponent("deliverables/video_rebuild/frames")
try FileManager.default.createDirectory(at: output, withIntermediateDirectories: true)
let asset = AVURLAsset(url: input)
let generator = AVAssetImageGenerator(asset: asset)
generator.appliesPreferredTrackTransform = true
generator.maximumSize = CGSize(width: 1280, height: 720)
for second in stride(from: 0.0, through: 270.0, by: 15.0) {
    let image = try generator.copyCGImage(at: CMTime(seconds: second, preferredTimescale: 600), actualTime: nil)
    let bitmap = NSBitmapImageRep(cgImage: image)
    guard let png = bitmap.representation(using: .png, properties: [:]) else { continue }
    try png.write(to: output.appendingPathComponent(String(format: "%03.0f.png", second)))
}
