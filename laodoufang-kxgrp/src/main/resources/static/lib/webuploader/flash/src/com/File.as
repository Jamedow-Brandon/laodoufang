package
static.
lib.webuploader.flash.src.com
static.
lib.webuploader.flash.src.com
{
    public class File extends Blob {
        public function File(sources:Array, properties:* = null) {
            if (properties is Object && properties.hasOwnProperty('name')) {
                _name = properties.name;
            }

            super(sources, properties);
        }

        private var _name:String = '';
        private var _lastModifiedDate:Date;

        public function get name():String {
            if (_name !== '') {
                return _name;
            }
            // if source is not a FileReference return default name
            if (!isFileRef()) {
                return uid;
            }
            // otherwise return original name
            return _sources[0].buffer.fileRef.name;
        }

        public function get lastModifiedDate():Date {
            if (!isFileRef()) {
                return new Date();
            }
            return _sources[0].buffer.fileRef.modificationDate;
        }

        public override function isFileRef():Boolean {
            return !!(_sources.length === 1 && _sources[0].buffer.fileRef);
        }


        public override function toObject():Object {
            return Utils.extend(super.toObject(), {
                name: name,
                lastModifiedDate: lastModifiedDate
            });
        }

    }
}