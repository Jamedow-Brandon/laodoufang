package
static.
lib.webuploader.flash.src.com
{
    public class Md5 extends OEventDispatcher {
        // events dispatched by this class
        public static var dispatches:Object = {
            "Progress": OProgressEvent.PROGRESS,
            "Complete": ODataEvent.DATA,
            "Load": Event.COMPLETE,
            "Error": OErrorEvent.ERROR
        };

        private static var ci:Object;
        private var ret:String;

        public function init(options:Object = null):void {
            Utils.extend(this, options, true);
        }

        public function loadFromBlob(blob:* = null):void {
            var fr:FileReader;


            if (typeof blob === 'string') {
                blob = Uploader.compFactory.get(blob);
            }

            if (!blob) {
                dispatchEvent(new OErrorEvent(OErrorEvent.ERROR, ImageError.WRONG_FORMAT));
                return;
            }

            fr = new FileReader;


            fr.addEventListener(ProgressEvent.PROGRESS, function (e:ProgressEvent):void {
                dispatchEvent(new OProgressEvent(OProgressEvent.PROGRESS, e.bytesLoaded, e.bytesTotal));
            });

            fr.addEventListener(Event.COMPLETE, function (e:Event):void {
                fr.removeAllEventsListeners();
                dispatchEvent(new Event(Event.COMPLETE));
                _loadFromByteArray(fr.result);
                dispatchEvent(new Event(ODataEvent.DATA));
            });

            fr.readAsByteArray(blob);
        }

        public function getResult():String {
            return ret;
        }

        public function destroy():void {
            // one call to mark any dereferenced objects and sweep away old marks,
            flash.system.System.gc();
            // ...and the second to now sweep away marks from the first call.
            flash.system.System.gc();
        }

        private function _loadFromByteArray(ba:ByteArray):void {
            if (!ci) {
                ci = new CLibInit().init();
            }

            ret = ci.md5BytesMethod(ba);
        }

    }
}