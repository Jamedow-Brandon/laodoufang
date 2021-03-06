package
static.
lib.webuploader.flash.src.com.events
static.
lib.webuploader.flash.src.com.events
{
    public class OProgressEvent extends ProgressEvent {
        public static const PROGRESS:String = 'webuploaderprogress';

        public function OProgressEvent(type:String, bytesLoaded:uint = 0, bytesTotal:uint = 0, data:* = null) {
            this.data = data;
            super(type, true, false, bytesLoaded, bytesTotal);
        }

        public var data:*;

        public override function clone():Event {
            return new OProgressEvent(type, bytesLoaded, bytesTotal, data);
        }
    }
}