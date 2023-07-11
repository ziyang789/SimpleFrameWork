<template>
    <div class="app-container">

<!--        <el-row>
            <div style="width:512px;height:512px;position:relative;color: white;display:inline-block;border-style:solid;border-color:black;"
                 oncontextmenu="return false"
                 class='disable-selection noIbar'
                 unselectable='on'
                 onselectstart='return false;'
                 onmousedown='return false;'>
                <div id="dicomImage"
                     style="width:512px;height:512px;top:0px;left:0px; position:absolute">
                </div>
            </div>
        </el-row>-->
        <el-row>
            <el-input class="form-control" type="text" :value="niftiURL"/>
            <el-button class="btn-default" @click="downloadAndView"> 加载nifti文件</el-button>
        </el-row>

        <el-row>
            <div oncontextmenu="return false"
                 class='disable-selection noIbar nifti-image-container'
                 unselectable='on'
                 onselectstart='return false;'
                 onmousedown='return false;'>
                <div ref="niftiImageContainer" class="nifti-image"></div>

                <!-- 画布上标签元素 -->

                <div id="topleft" class="overlay" style="position:absolute;top:0px;left:0px">
                    Patient Name
                </div>
                <div id="topright" class="overlay" style="position:absolute;top:0px;right:0px">
                    Hospital
                </div>
                <div id="bottomright" class="overlay" style="position:absolute;bottom:0px;right:0px">Zoom: 2x</div>
                <div id="bottomleft" class="overlay" style="position:absolute;bottom:0px;left:0px">WW/WC:247/138</div>
            </div>
        </el-row>

    </div>
</template>

<script setup name="temporary">
    import {getCurrentInstance, ref} from "vue";
    let { proxy } = getCurrentInstance();
    let loaded = false;

    // const cornerstoneWADOImageLoader = proxy.cornerstoneWADOImageLoader;

    const cornerstoneTools = proxy.cornerstoneTools;
    const cornerstone = proxy.cornerstone;
    const cornerstoneMath = proxy.cornerstoneMath;

    const cornerstoneNIFTIImageLoader = window.cornerstoneNIFTIImageLoader;
    const ImageId = cornerstoneNIFTIImageLoader.nifti.ImageId;
    cornerstoneNIFTIImageLoader.external.cornerstone = cornerstone;

    cornerstoneTools.external.Hammer = window.Hammer;
    cornerstoneTools.external.cornerstone = cornerstone;
    cornerstoneTools.external.cornerstoneMath = cornerstoneMath;

    cornerstoneTools.init({
        showSVGCursors: true,
    });

    const niftiURL = ref('http://localhost:8089/1.gz');
    const niftiImageContainer = ref();
    const downloadAndView = () => {
        let url = niftiURL.value;
        loadAndViewImage(`nifti:${url}`);
    }

    const loadAndViewImage = (imageId) => {
        const element = proxy.$refs.niftiImageContainer;
        const imageIdObject = ImageId.fromURL(imageId);
        cornerstone.enable(element);
        try {
            cornerstone.loadAndCacheImage(imageIdObject.url).then(function(image) {
                console.log(image);
                const numberOfSlices = cornerstone.metaData.get('multiFrameModule', imageIdObject.url).numberOfFrames;
                const stack = {
                    currentImageIdIndex: imageIdObject.slice.index,
                    imageIds: Array.from(Array(numberOfSlices), (_, i) => `nifti:${imageIdObject.filePath}#${imageIdObject.slice.dimension}-${i}`)
                };

                const viewport = cornerstone.getDefaultViewportForImage(element, image);
                cornerstone.displayImage(element, image, viewport);
                if(loaded === false) {
                    cornerstoneTools.addStackStateManager(element, ['stack', 'wwwc', 'pan', 'zoom', 'move']);
                    cornerstoneTools.addToolState(element, 'stack', stack);

                    cornerstoneTools.addTool(cornerstoneTools.WwwcTool);
                    cornerstoneTools.setToolActive('Wwwc', { mouseButtonMask: 2 });

                    cornerstoneTools.addTool(cornerstoneTools.ZoomTool, {
                        // Optional configuration
                        configuration: {
                            invert: false,
                            preventZoomOutsideImage: false,
                            minScale: .1,
                            maxScale: 20.0,
                        }
                    });
                    cornerstoneTools.setToolActive('Zoom', { mouseButtonMask: 1 });
                    loaded = true;
                }
            }, function(err) {
                console.error(err);
            });
        } catch(err) {
            console.error(err);
        }
    }

    const openLengthTool = () => {
        // Add our tool, and set it's mode
        cornerstoneTools.addTool(cornerstoneTools.LengthTool)
        cornerstoneTools.setToolActive('Length', { mouseButtonMask: 1 })
    }

    const closeLengthTool = () => {
        cornerstoneTools.setToolEnabled('Length', { mouseButtonMask: 1 })
    }



</script>

<style lang="scss" scoped>

.nifti-image-container {
    position: relative;
    width: 512px;
    height: 512px;
    display: inline-block;
    border-style: solid;
    border-color: black;
    color: white;
    margin-right: 1rem;
}

.nifti-image {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}
</style>